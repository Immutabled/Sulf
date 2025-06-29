package git.immutabled.sulf.spigot.freeze.connector

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.spigot.FREEZE_PID
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.freeze.FreezeRegistry
import java.util.*

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 19:05
 */
class FreezePacketListener(
    private val plugin: Plugin,
    private  val registry: FreezeRegistry
) : PacketListener {

    @Data(id = FREEZE_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val id = data.getString("id") ?: return
        val uuid = UUID.fromString(id)

        if (this.registry.freezes.contains(uuid)) {
            this.registry.freezes.remove(uuid)
            return
        }

        this.registry.freezes.add(uuid)
    }
}