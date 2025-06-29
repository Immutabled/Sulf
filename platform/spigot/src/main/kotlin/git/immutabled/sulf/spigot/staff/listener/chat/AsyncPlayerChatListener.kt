package git.immutabled.sulf.spigot.staff.listener.chat

import git.immutabled.sulf.api.events.staff.PlayerStaffChatEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_CHAT_METADATA_KEY
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 15:53
 */
class AsyncPlayerChatListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        if (!event.player.hasMetadata(STAFF_CHAT_METADATA_KEY)) {
            return
        }

        event.isCancelled = true

        Bukkit.getScheduler().runTask(plugin, Runnable {
            Bukkit.getPluginManager().callEvent(
                PlayerStaffChatEvent(
                    event.player,
                    event.message
                )
            )
        })
    }
}