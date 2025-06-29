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
class TikTokCommand(
    private val network: NetworkConfig
)  : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage(" ");
        sender.sendMessage("${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}Cuenta de TikTok");
        sender.sendMessage("${ChatColor.DARK_PURPLE}► ${ChatColor.LIGHT_PURPLE}${network.tiktok}");
        sender.sendMessage(" ");
        return true
    }
}