package git.immutabled.sulf.spigot.staff.connector

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import git.immutabled.sulf.spigot.STAFF_PID
import git.immutabled.sulf.spigot.util.ComponentAdapter

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 17:22
 */
class StaffAlertPacketListener(
    private val plugin: Plugin
) : PacketListener {

    @Data(id = STAFF_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val componentSerialized = data.getString("component") ?: return
        val component = ComponentAdapter.fromJson(componentSerialized) ?: throw IllegalArgumentException("Invalid component")

        this.plugin.server.onlinePlayers.forEach { player ->

            if (player.hasPermission(STAFF_PERMISSION)) {
                player.sendMessage(component)
            }
        }
    }
}