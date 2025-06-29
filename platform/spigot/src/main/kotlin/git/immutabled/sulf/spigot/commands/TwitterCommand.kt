package git.immutabled.sulf.spigot.commands

import git.immutabled.sulf.spigot.NetworkConfig
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since March 24, 04:44
 */
class TwitterCommand(
    private val network: NetworkConfig
)  : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage(" ");
        sender.sendMessage("${ChatColor.AQUA}${ChatColor.BOLD}Cuenta de Twitter");
        sender.sendMessage("${ChatColor.DARK_AQUA}► ${ChatColor.AQUA}${network.twitter}");
        sender.sendMessage(" ");
        return true
    }
}