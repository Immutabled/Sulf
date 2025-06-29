package git.immutabled.sulf.velocity.staff.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.ClickEvent.clickEvent
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
class DisconnectListener(
    private val plugin: SulfPlugin
) {

    @Subscribe
    fun onStaffAlert(event: DisconnectEvent) {
        if (!event.player.hasPermission(STAFF_PERMISSION)) return

        val message = Component.text()
            .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
            .also {
                event.player.currentServer.ifPresent { server ->
                    it.append(Component.text("[").color(NamedTextColor.GRAY))
                    it.append(Component.text(server.server.serverInfo.name).color(NamedTextColor.GRAY))
                        .apply {
                            clickEvent(ClickEvent.Action.RUN_COMMAND, "/server ${server.server.serverInfo.name}")
                        }
                        .append(Component.text("] ").color(NamedTextColor.GRAY))
                }
            }
            .append(Component.text(plugin.hook.getDisplayName(event.player))).color(NamedTextColor.WHITE)
            .append(Component.text(": ").color(NamedTextColor.GRAY))
            .append(Component.text("has logged out from the network!").color(NamedTextColor.RED))
            .build()

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                message
            )
        )
    }
}