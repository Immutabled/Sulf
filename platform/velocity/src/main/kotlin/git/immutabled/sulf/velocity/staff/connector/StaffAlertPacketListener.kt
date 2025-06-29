package git.immutabled.sulf.velocity.staff.connector

import net.kyori.adventure.text.Component
import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.velocity.STAFF_ALERT_PID
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.util.ComponentAdapter

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:32
 */
object StaffAlertPacketListener  : PacketListener {

    @Data(id = STAFF_ALERT_PID)
    fun onStaffAlertEvent(data: JsonObject) {
        SulfPlugin.get().server.consoleCommandSource.sendMessage(
            Component.text("Staff alert packet received")
        )

        try {
            val componentSerialized = data.getString("component")
                ?: throw IllegalArgumentException("Missing 'component' in packet listener")

            val component = ComponentAdapter.fromJson(componentSerialized) ?: throw IllegalArgumentException("Failed to deserialize component")

            SulfPlugin.get().server.allPlayers.forEach { player ->
                if (player.hasPermission(STAFF_PERMISSION)) {
                    player.sendMessage(component)
                }
            }

        } catch (e: Exception) {
            println("Error processing staff alert packet: ${e.message}")
            e.printStackTrace()
        }
    }
}