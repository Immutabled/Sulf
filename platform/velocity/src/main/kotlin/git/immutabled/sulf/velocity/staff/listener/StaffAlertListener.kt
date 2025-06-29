package git.immutabled.sulf.velocity.staff.listener

import com.velocitypowered.api.event.Subscribe
import git.immutabled.connector.Connector
import git.immutabled.sulf.velocity.STAFF_ALERT_PID
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.util.ComponentAdapter

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:30
 */
class StaffAlertListener(private val connector: Connector) {

    @Subscribe
    fun onStaffAlert(event: StaffAlertEvent) {

        this.connector.sendPacket(
            STAFF_ALERT_PID,
            mapOf(
                "component" to ComponentAdapter.toJson(event.message)
            )
        )
    }
}