package git.immutabled.sulf.velocity.proxy.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.proxy.ProxyModule
import git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:43
 */
class PlayerChooseInitialServerListener(
    private val plugin: SulfPlugin,
    val module: ProxyModule
) {

    @Subscribe
    fun onProxyInitialization(event: PlayerChooseInitialServerEvent) {

        if (!module.proxy.players.contains(event.player.uniqueId)) {
            this.plugin.server.eventManager.fireAndForget(
                git.immutabled.sulf.velocity.proxy.event.ProxyUpdateEvent(
                    "players",
                    event.player.uniqueId
                )
            )
        }

        plugin.connector.sendACross(mapOf(
            "players" to module.total(),
            "slots" to module.maximum()
        ))
    }
}