package git.immutabled.sulf.spigot.assistance.connector

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.spigot.ASSISTANCE_PID
import git.immutabled.sulf.spigot.assistance.AssistanceRegistry
import java.util.*

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 19:08
 */
class AssistancePacketListener(
    private val registry: AssistanceRegistry
) : PacketListener {

    @Data(id = ASSISTANCE_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val id = data.getString("id") ?: return
        val uuid = UUID.fromString(id)

        this.registry.addReport(uuid)
    }
}