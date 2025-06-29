package git.immutabled.sulf.spigot.staff.command

import net.md_5.bungee.api.ChatColor
import git.immutabled.sulf.api.events.staff.PlayerStaffChatEvent
import git.immutabled.sulf.spigot.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:19
 */
class StaffChatCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this command.")
            return true
        }

        if (!sender.hasPermission(STAFF_PERMISSION)) {
            sender.sendMessage(NO_PERMISSION_MESSAGE)
            return true
        }

        if (args.isEmpty()) {
            if (sender.hasMetadata(STAFF_CHAT_METADATA_KEY)) {
                sender.removeMetadata(STAFF_CHAT_METADATA_KEY, Plugin.instance)
            } else {
                sender.setMetadata(STAFF_CHAT_METADATA_KEY, FixedMetadataValue(Plugin.instance, true))
            }

            sender.sendMessage("${ChatColor.GOLD}Staff Chat: ${if (sender.hasMetadata(STAFF_CHAT_METADATA_KEY)) "${ChatColor.GREEN}Enabled" else "${ChatColor.RED}Disabled"}")
            return true
        }

        val text = args.joinToString(" ")
        Bukkit.getServer().pluginManager.callEvent(PlayerStaffChatEvent(sender, text))
    return true
    }
}
