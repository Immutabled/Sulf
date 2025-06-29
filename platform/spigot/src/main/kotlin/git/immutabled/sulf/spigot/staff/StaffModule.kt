package git.immutabled.sulf.spigot.staff

import git.immutabled.sulf.api.events.staff.PlayerModEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.commands.listener.BroadcastPacketListener
import git.immutabled.sulf.spigot.staff.command.BuildCommand
import git.immutabled.sulf.spigot.staff.command.ModModeCommand
import git.immutabled.sulf.spigot.staff.command.StaffChatCommand
import git.immutabled.sulf.spigot.staff.command.VanishCommand
import git.immutabled.sulf.spigot.staff.connector.StaffAlertPacketListener
import git.immutabled.sulf.spigot.staff.items.listener.*
import git.immutabled.sulf.spigot.staff.listener.StaffAlertListener
import git.immutabled.sulf.spigot.staff.listener.build.PlayerBuildListener
import git.immutabled.sulf.spigot.staff.listener.chat.AsyncPlayerChatListener
import git.immutabled.sulf.spigot.staff.listener.chat.PlayerStaffChatListener
import git.immutabled.sulf.spigot.staff.listener.mod.PlayerJoinListener
import git.immutabled.sulf.spigot.staff.listener.mod.PlayerModListener
import git.immutabled.sulf.spigot.staff.listener.mod.PlayerQuitListener
import git.immutabled.sulf.spigot.staff.listener.staff.StaffJoinNetworkListener
import git.immutabled.sulf.spigot.staff.listener.vanish.*
import git.immutabled.sulf.spigot.staff.listener.vanish.PlayerInteractEntityListener
import git.immutabled.sulf.spigot.staff.service.StaffActionBar
import org.bukkit.command.CommandExecutor
import git.immutabled.sulf.spigot.staff.listener.vanish.PlayerJoinListener as VanishPlayerJoinListener
import git.immutabled.sulf.spigot.staff.listener.vanish.PlayerQuitListener as VanishPlayerQuitListener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:13
 */
class StaffModule(
    private val registry: StaffRegistry
) : PluginModule {

    override fun load(plugin: Plugin) {
        plugin.getConnector().addListener(BroadcastPacketListener(plugin))

        StaffActionBar(plugin).runTaskTimer(plugin, 0L, 20L)

        // Vanish
        plugin.server.pluginManager.registerEvents(EntityDamageByEntityListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerInteractEntityListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerVanishListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(VanishBlockListener(), plugin)
        plugin.server.pluginManager.registerEvents(VanishPlayerJoinListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(VanishPlayerQuitListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(PlayerRespawnListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(PlayerTeleportListener(plugin), plugin)

        // Chat
        plugin.server.pluginManager.registerEvents(PlayerStaffChatListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(AsyncPlayerChatListener(plugin), plugin)

        // Build
        plugin.server.pluginManager.registerEvents(PlayerBuildListener(plugin), plugin)

        // Staff
        plugin.server.pluginManager.registerEvents(PlayerJoinListener(plugin, this.registry), plugin)
        plugin.server.pluginManager.registerEvents(PlayerModListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(PlayerQuitListener(plugin, this.registry), plugin)

        plugin.server.pluginManager.registerEvents(StaffJoinNetworkListener(plugin), plugin)

        plugin.getConnector().addListener(StaffAlertPacketListener(plugin))
        plugin.server.pluginManager.registerEvents(StaffAlertListener(plugin), plugin)

        // items
        plugin.server.pluginManager.registerEvents(InventoryClickListener(this.registry), plugin)
        plugin.server.pluginManager.registerEvents(PlayerDropItemListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerInteractEntityListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerInteractListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerPickupItemListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerSwapHandItemsListener(this.registry), plugin)
        plugin.server.pluginManager.registerEvents(RestoreInventoryListener(this.registry), plugin)
        plugin.server.pluginManager.registerEvents(SetItemsListener(this.registry), plugin)
    }

    override fun unload(plugin: Plugin) {
        plugin.server.onlinePlayers.filter {
            it.hasMetadata(STAFF_METADATA_KEY)
        }.forEach {
            plugin.server.pluginManager.callEvent(
                PlayerModEvent(it)
            )
        }
    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return mapOf(
            listOf("vanish", "v", "invisible", "invis", "hide") to VanishCommand(),
            listOf("staffchat", "sc") to StaffChatCommand(),
            listOf("modmode", "mod", "staff", "hackermode") to ModModeCommand(),
            listOf("build") to BuildCommand()
        )
    }
}