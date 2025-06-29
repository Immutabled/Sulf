package git.immutabled.sulf.spigot.commands.listener

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.spigot.BROADCAST_PID
import git.immutabled.sulf.spigot.Plugin
import org.bukkit.ChatColor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 18:31
 */
class BroadcastPacketListener(
    private val plugin: Plugin
) : PacketListener {

    @Data(id = BROADCAST_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val text = data.getString("text") ?: return

        this.plugin.server.onlinePlayers.forEach { player ->
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', text))
        }
    }
}