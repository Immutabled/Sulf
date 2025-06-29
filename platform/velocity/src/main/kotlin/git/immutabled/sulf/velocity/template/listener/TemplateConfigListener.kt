package git.immutabled.sulf.velocity.template.listener

import com.velocitypowered.api.event.Subscribe
import git.immutabled.connector.Connector
import git.immutabled.sulf.velocity.TEMPLATE_CONFIG_PID
import git.immutabled.sulf.velocity.template.event.TemplateConfigEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:53
 */
class TemplateConfigListener(
    private val connector: Connector
)  {

    @Subscribe
    fun onWhitelistConfig(event: TemplateConfigEvent) {
        this.connector.sendPacket(
            TEMPLATE_CONFIG_PID,
            mapOf(
                "key" to event.key,
                "identifier" to event.id,
                "value" to event.value
            )
        )
    }
}