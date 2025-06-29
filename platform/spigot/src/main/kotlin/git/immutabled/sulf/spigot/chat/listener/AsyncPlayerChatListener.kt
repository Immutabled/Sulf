package git.immutabled.sulf.spigot.chat.listener

import git.immutabled.moshi.TimeUtil
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import git.immutabled.sulf.spigot.chat.ChatModule
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 17:48
 */
class AsyncPlayerChatListener(
    private val module: ChatModule
) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onAsyncPlayerChat(event: AsyncPlayerChatEvent) {

        val player = event.player

        if (this.module.muted && !event.player.hasPermission(STAFF_PERMISSION)) {
            event.isCancelled = true
            event.player.sendMessage(ChatColor.RED.toString() + "Public chat is currently muted.")
            return
        }

        if (this.module.slowed && !event.player.hasPermission("modsuite.command.slowchat")) {
            val lastMessage: Long = this.module.getLastMessageAgo(event.player.uniqueId)

            if (lastMessage != 0L && lastMessage < this.module.slowTime) {
                event.isCancelled = true
                event.player.sendMessage("${ChatColor.RED}You cannot talk in public chat for another ${ChatColor.BOLD}${TimeUtil.formatIntoFancy(this.module.slowTime - lastMessage)}${ChatColor.RED}.")
                return
            }
        }

//        event.isCancelled = true
        module.setLastMessage(player.uniqueId, System.currentTimeMillis())
    }
}