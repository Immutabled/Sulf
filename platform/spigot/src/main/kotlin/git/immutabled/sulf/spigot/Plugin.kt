package git.immutabled.sulf.spigot

import git.immutabled.connector.Connector
import git.immutabled.moshi.ConfigUtil
import git.immutabled.sulf.nms.BukkitNMS
import git.immutabled.sulf.nms.paper.PaperNMS
import git.immutabled.sulf.nms.purpur.PurpurNMS
import git.immutabled.sulf.spigot.assistance.AssistanceModule
import git.immutabled.sulf.spigot.assistance.AssistanceRegistry
import git.immutabled.sulf.spigot.assistance.VelocityBridge
import git.immutabled.sulf.spigot.chat.ChatModule
import git.immutabled.sulf.spigot.commands.*
import git.immutabled.sulf.spigot.cross.CrossModule
import git.immutabled.sulf.spigot.discord.DiscordModule
import git.immutabled.sulf.spigot.freeze.FreezeModule
import git.immutabled.sulf.spigot.freeze.FreezeRegistry
import git.immutabled.sulf.spigot.hook.CoreHook
import git.immutabled.sulf.spigot.hook.DefaultImpl
import git.immutabled.sulf.spigot.hook.PlaceholderImpl
import git.immutabled.sulf.spigot.staff.StaffModule
import git.immutabled.sulf.spigot.staff.StaffRegistry
import git.immutabled.sulf.spigot.support.SupportModule
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.plugin.java.JavaPlugin
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.lang.reflect.Field

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since March 24, 04:41
 */

class Plugin : JavaPlugin() {

    var bridge: VelocityBridge? = null
    private var assistanceRegistry: AssistanceRegistry? = null
    private var freezeRegistry: FreezeRegistry? = null
    private var staffRegistry: StaffRegistry? = null

    private val modules = mutableListOf<PluginModule>()

    private var jedis: JedisPool? = null
    private var connector: Connector? = null

    var coreHook: CoreHook = DefaultImpl()

    var players = 0
    var slots = 0

    var placeholder = false

    lateinit var config: PluginConfig

    override fun onEnable() {
        instance = this
        config =  ConfigUtil.register("config", this.name, this.dataFolder, PluginConfig())
        ConfigUtil.save(config)
        placeholder = this.server.pluginManager.isPluginEnabled("PlaceholderAPI")

        val version = Bukkit.getVersion().lowercase()

        nms = when {
            "purpur" in version -> PurpurNMS()
            "paper" in version -> PaperNMS()
            else -> throw UnsupportedOperationException("Unsupported server type or version: $version")
        }

        this.jedis = JedisPool()
        this.connector = Connector("Sulf", jedis ?: throw IllegalStateException("Failed to create JedisPool"))

        this.registerCommand("twitter", TwitterCommand(this.config.network), listOf("x"))
        this.registerCommand("discord", DiscordCommand(this.config.network), listOf("dc"))
        this.registerCommand("instagram", InstagramCommand(this.config.network), listOf("ig"))
        this.registerCommand("tiktok", TikTokCommand(this.config.network))
        this.registerCommand("website", WebsiteCommand(this.config.network), listOf("web", "webregister"))
        this.registerCommand("store", StoreCommand(this.config.network), listOf("tienda"))
        this.registerCommand("vote", VoteCommand(this.config.network), listOf("namemc"))
        this.registerCommand("bcrawall", BroadcastCommand(), listOf("alert"))
        this.registerCommand("uuid", UUIDCommand(this))

        this.bridge = VelocityBridge(this)

        this.assistanceRegistry = AssistanceRegistry()
        this.modules.add(AssistanceModule(this.bridge!!, this.assistanceRegistry ?: throw IllegalStateException("AssistanceRegistry is null")))

        this.freezeRegistry = FreezeRegistry()
        this.modules.add(FreezeModule(this.freezeRegistry ?: throw IllegalStateException("FreezeRegistry is null")))

        this.staffRegistry = StaffRegistry()
        this.modules.add(StaffModule(staffRegistry ?: throw IllegalStateException("StaffRegistry is null")))
        this.modules.add(ChatModule())
        this.modules.add(CrossModule())
        this.modules.add(SupportModule())
        this.modules.add(DiscordModule())

        this.modules.forEach {
            it.load(this)
            it.commands().forEach { command ->
                this.registerCommand(command.key[0], command.value, command.key)
            }

            if (this.placeholder) {
                    it.expansions(this).forEach { expansion ->
                        expansion.register()
                    }
            }
        }

        if (this.placeholder) {
            this.coreHook = PlaceholderImpl()
        }
    }

    override fun onDisable() {
        this.modules.forEach { it.unload(this) }
    }

    fun registerCommand(commandName: String, executor: CommandExecutor, aliases: List<String> = listOf()) {
        try {
            val server = Bukkit.getServer()
            val commandMapField: Field = server.javaClass.getDeclaredField("commandMap")
            commandMapField.isAccessible = true
            val commandMap: CommandMap = commandMapField.get(server) as CommandMap

            val command = object : BukkitCommand(commandName, "", "", aliases.drop(1)) {
                override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                    return executor.onCommand(sender, this, label, args)
                }
            }

            commandMap.register(this.name.lowercase(), command)
        } catch (e: Exception) {
            this.logger.severe("Could not register command $commandName: ${e.message}")
        }
    }

    fun getConnector(): Connector {
        return this.connector ?: throw IllegalStateException("Connector has not been initialized.")
    }

    companion object {
        lateinit var instance: Plugin
        lateinit var nms: BukkitNMS
    }
}
