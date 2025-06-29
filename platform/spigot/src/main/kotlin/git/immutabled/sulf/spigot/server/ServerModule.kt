package git.immutabled.sulf.spigot.server

import git.immutabled.moshi.ConfigUtil
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import org.bukkit.command.CommandExecutor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 10, 21:37
 */
class ServerModule : PluginModule {

    lateinit var server: ServerInfo
    lateinit var serverName: String

    lateinit var config: ServerConfig

    override fun load(plugin: Plugin) {
        this.config = ConfigUtil.register("servers", "Sulf", plugin.dataFolder, ServerConfig())
        this.serverName = plugin.config.serverName

        this.config.servers.find {
            it.name.equals(this.serverName, true)
        }?.let {
            this.server = it
        } ?: run {
            this.server = ServerInfo(
                name = this.serverName,
                port = plugin.server.port,
                status = Status.ONLINE,
                players = 0,
                maxPlayers = 0,
                metadata = mapOf(),
                uptime = 0,
                onlineMode = plugin.server.onlineMode,
                bukkitVersion = plugin.server.bukkitVersion
            )
        }
    }

    override fun unload(plugin: Plugin) {
        this.server.status = Status.OFFLINE
    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return mapOf()
    }
}