package git.immutabled.sulf.spigot.staff.listener.staff

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.ClickEvent.clickEvent
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.api.events.staff.StaffAlertEvent
import git.immutabled.sulf.api.events.staff.logging.StaffJoinNetworkEvent
import git.immutabled.sulf.spigot.Plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 16:56
 */
class StaffJoinNetworkListener(
    val plugin: Plugin
): Listener {

    @EventHandler
    fun onStaffJoinNetwork(event: StaffJoinNetworkEvent) {
            val message = Component.text()
                .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
                .append(Component.text("[").color(NamedTextColor.GRAY))
                .append(Component.text(event.server).color(NamedTextColor.GRAY))
                .apply {
                    clickEvent(ClickEvent.Action.RUN_COMMAND, "/server $${event.server}")
                }
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(event.player.displayName()).color(NamedTextColor.WHITE)
                .append(Component.text(": ").color(NamedTextColor.GRAY))
                .append(Component.text("has joined to the network!").color(NamedTextColor.GREEN))

            this.plugin.server.pluginManager.callEvent(StaffAlertEvent(message.build()))
    }

}
