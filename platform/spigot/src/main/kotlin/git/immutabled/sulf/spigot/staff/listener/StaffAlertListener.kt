package git.immutabled.sulf.spigot.staff.listener

import git.immutabled.sulf.api.events.staff.StaffAlertEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PID
import git.immutabled.sulf.spigot.util.ComponentAdapter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 17:24
 */
class StaffAlertListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onStaffAlert(event: StaffAlertEvent) {
        plugin.getConnector().sendPacket(
            STAFF_PID,
            mapOf(
                "component" to ComponentAdapter.toJson(event.component)
            )
        )
    }
}