package git.immutabled.sulf.velocity.proxy

import git.immutabled.moshi.ConfigUtil
import git.immutabled.moshi.MoshiUtil
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.PluginModule
import git.immutabled.sulf.velocity.proxy.command.GListCommand
import git.immutabled.sulf.velocity.proxy.command.HubCommand
import git.immutabled.sulf.velocity.proxy.connector.ProxyUpdatePacketListener
import git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent
import git.immutabled.sulf.velocity.proxy.listener.*
import java.util.*

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:00
 */
class ProxyModule : PluginModule {

    lateinit var proxy: ProxyInfo
    lateinit var config: ProxyConfig

    override fun load(plugin: SulfPlugin) {
        this.config = ConfigUtil.register("proxies", "Sulf", plugin.dataDirectory.toFile(), ProxyConfig())

        this.config.proxies.find {
            it.name.equals(plugin.config.identifier, true)
        }?.let {
            this.proxy = it
        } ?: run {
            this.proxy = ProxyInfo(
                name = plugin.config.identifier,
                port = plugin.server.boundAddress.port,
                region = plugin.config.region,
                maxPlayers = plugin.config.maxPlayers
            )
        }

        plugin.server.commandManager.register("lobby", HubCommand(plugin), "salir", "hub")
        plugin.server.commandManager.register("glist", GListCommand(plugin, this))
        plugin.server.eventManager.register(plugin, DisconnectListener(plugin, this))
        plugin.server.eventManager.register(plugin, PlayerChooseInitialServerListener(plugin, this))
        plugin.server.eventManager.register(plugin, ProxyInitializeListener(plugin, this))
        plugin.server.eventManager.register(plugin, ProxyShutdownListener(plugin, this))
        plugin.server.eventManager.register(plugin, ProxyUpdateListener(plugin.connector, this))

        plugin.connector.addListener(ProxyUpdatePacketListener)
    }

    fun total() : Int {
        return this.config.proxies.sumOf { it.players.size }
    }

    fun players() : List<UUID> {
        return this.config.proxies.flatMap { it.players }
    }

    fun maximum() : Int {
        return this.config.proxies.sumOf { it.maxPlayers }
    }

    override fun unload(plugin: SulfPlugin) {
        ConfigUtil.save(config)
    }
}