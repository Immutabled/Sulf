package git.immutabled.sulf.velocity.whitelist.listener

import com.velocitypowered.api.event.Subscribe
import net.kyori.adventure.text.Component
import git.immutabled.connector.Connector
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.WHITELIST_CONFIG_PID
import git.immutabled.sulf.velocity.whitelist.event.WhitelistConfigEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:53
 */
class WhitelistConfigListener(
    private val connector: Connector,
)  {

    @Subscribe
    fun onWhitelistConfig(event: WhitelistConfigEvent) {
        this.connector.sendPacket(
            WHITELIST_CONFIG_PID,
            mapOf(
                "id" to event.key,
                "value" to event.value,
                "debug" to "yes"
            )
        )
    }
}