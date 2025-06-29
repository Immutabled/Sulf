package git.immutabled.sulf.velocity.proxy.listener

import com.velocitypowered.api.event.Subscribe
import git.immutabled.connector.Connector
import git.immutabled.sulf.velocity.PROXY_UPDATE_PID
import git.immutabled.sulf.velocity.proxy.ProxyModule
import git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:29
 */
class ProxyUpdateListener(
    private val connector: Connector,
    private val module: ProxyModule
) {

    @Subscribe
    fun onWhitelistConfig(event: git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent) {
        this.connector.sendPacket(
            PROXY_UPDATE_PID,
            mapOf(
                "key" to event.key,
                "id" to module.proxy.name,
                "value" to event.value
            )
        )
    }
}