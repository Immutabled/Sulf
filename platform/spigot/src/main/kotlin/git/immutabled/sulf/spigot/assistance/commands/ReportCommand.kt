package git.immutabled.sulf.spigot.assistance.commands

import net.md_5.bungee.api.ChatColor
import git.immutabled.sulf.api.events.assistance.AssistanceEvent
import git.immutabled.sulf.spigot.assistance.AssistanceRegistry
import org.bukkit.Bukkit
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
class ReportCommand(
    private val registry: AssistanceRegistry
)  : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this command.")
            return true
        }

        if (args.size < 2) {
            sender.sendMessage("${ChatColor.RED}Usage: /report <player> <reason>")
            return true
        }

        val target = Bukkit.getPlayer(args[0])
        if (target == null) {
            sender.sendMessage("${ChatColor.RED}Player not found.")
            return true
        }

        if (target == sender) {
            sender.sendMessage("${ChatColor.RED}You cannot report yourself.")
            return true
        }

        val reason = args.drop(1).joinToString(" ")
        Bukkit.getServer().pluginManager.callEvent(
            AssistanceEvent(
                sender,
                reason,
                target
            )
        )
        return true
    }
}