package git.immutabled.sulf.velocity.proxy.listener

import com.velocitypowered.api.event.Subscribe
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.proxy.ProxyModule
import git.immutabled.sulf.velocity.proxy.event.ProxyUpEvent
import git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:06
 */
class ProxyInitializeListener(
    private val plugin: SulfPlugin,
    val module: ProxyModule
) {

    @Subscribe
    fun onProxyInitialization(event: ProxyUpEvent) {

        this.plugin.server.eventManager.fireAndForget(
            git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent(
                "added",
                mapOf(
                    "name" to this.plugin.config.identifier,
                    "port" to this.plugin.server.boundAddress.port,
                    "region" to this.plugin.config.region,
                    "maxPlayers" to this.plugin.config.maxPlayers,
                    "players" to 0,
                )
            )
        )

    }
}