package git.immutabled.sulf.spigot.assistance.commands

import git.immutabled.sulf.api.events.assistance.AssistanceEvent
import git.immutabled.sulf.spigot.assistance.AssistanceRegistry
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since March 24, 05:20
 */
class HelpCommand(
    private val registry: AssistanceRegistry
)   : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this command.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}Usage: /helpop <reason>")
            return true
        }

//        if (this.registry.hasCooldown(sender)) {
//            val remaining = registry.getCooldown(sender)
//            val minutes = remaining / 60000
//            val seconds = (remaining % 60000) / 1000
//
//            sender.sendMessage("${org.bukkit.ChatColor.RED}You must wait $minutes minutes and $seconds seconds before using this again.")
//            return true
//        }

        val reason = args.joinToString(" ")
        Bukkit.getServer().pluginManager.callEvent(AssistanceEvent(sender, reason))
        return true
    }
}