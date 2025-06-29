package git.immutabled.sulf.spigot.commands;

import git.immutabled.sulf.spigot.BROADCAST_PID
import git.immutabled.sulf.spigot.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 12:58
 */
class BroadcastCommand : CommandExecutor {

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
            sender.sendMessage("${ChatColor.RED}Usage: /alert <reason>")
            return true
        }

        sender.sendMessage("${ChatColor.GREEN}Broadcast sent to all network.")

        val text = args.joinToString(" ")
        Plugin.instance.getConnector().sendPacket(BROADCAST_PID, mapOf(
            "text" to text
        ))

        return true
    }
}
