package git.immutabled.sulf.spigot.freeze.command

import net.md_5.bungee.api.ChatColor
import git.immutabled.sulf.api.events.freeze.PlayerFreezeEvent
import git.immutabled.sulf.spigot.FREEZE_METADATA_KEY
import git.immutabled.sulf.spigot.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:02
 */
class FreezeCommand : CommandExecutor {

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
            sender.sendMessage("${ChatColor.RED}Usage: /freeze <player>")
            return true
        }

        val target = Bukkit.getPlayer(args[0])
        if (target == null) {
            sender.sendMessage("${ChatColor.RED}Player not found.")
            return true
        }

        if (target == sender) {
            sender.sendMessage("${ChatColor.RED}You cannot freeze yourself.")
            return true
        }

        Bukkit.getServer().pluginManager.callEvent(git.immutabled.sulf.api.events.freeze.PlayerFreezeEvent(target))

        if (target.hasMetadata(FREEZE_METADATA_KEY)) {
            sender.sendMessage("${ChatColor.GREEN}You have unfrozen ${target.name}.")
        } else {
            sender.sendMessage("${ChatColor.RED}You have frozen ${target.name}.")
        }

        return true
    }

}