package git.immutabled.sulf.velocity.staff.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerConnectedEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:40
 */
class ServerConnectedListener(
    private val plugin: SulfPlugin
) {

    @Subscribe
    fun onStaffAlert(event: ServerConnectedEvent) {
        if (!event.player.hasPermission(STAFF_PERMISSION)) return

        val message = Component.text()
            .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
            .append(Component.text(plugin.hook.getDisplayName(event.player)).color(NamedTextColor.WHITE))
            .append(
                event.previousServer
                    .map {
                        Component.text(" switch to ").color(NamedTextColor.GREEN)
                        .append(Component.text(event.server.serverInfo.name).color(NamedTextColor.YELLOW))
                        .append(Component.text(" from ").color(NamedTextColor.GREEN))
                        .append(Component.text(it.serverInfo.name).color(NamedTextColor.RED))
                    }
                    .orElse(
                        Component.text(" [${event.server.serverInfo.name}] ").color(NamedTextColor.GRAY)
                            .append(Component.text("has joined to the network!").color(NamedTextColor.GREEN))
                    )
            )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                message.build()
            )
        )
    }
}