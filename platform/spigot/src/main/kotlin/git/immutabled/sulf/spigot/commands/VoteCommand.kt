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
 * @since abril 05, 19:19
 */
class VoteCommand(
    private val network: NetworkConfig
)  : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage(" ");
        sender.sendMessage("${ChatColor.AQUA}${ChatColor.BOLD}Enlace de NameMC");
        sender.sendMessage("${ChatColor.DARK_AQUA}► ${ChatColor.AQUA}${network.votes}");
        sender.sendMessage(" ");
        return true
    }
}