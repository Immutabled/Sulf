package git.immutabled.sulf.velocity.proxy.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.proxy.ProxyModule
import git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:06
 */
class ProxyShutdownListener(
    private val plugin: SulfPlugin,
    val module: ProxyModule
) {

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        this.plugin.server.eventManager.fireAndForget(
            git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent(
                "shutdown",
                0
            )
        )

        plugin.connector.sendACross(mapOf(
            "players" to module.total()
        ))
    }

}