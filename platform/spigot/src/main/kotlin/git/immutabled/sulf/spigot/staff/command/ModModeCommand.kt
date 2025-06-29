package git.immutabled.sulf.spigot.staff.command

import net.md_5.bungee.api.ChatColor
import git.immutabled.sulf.api.events.staff.PlayerModEvent
import git.immutabled.sulf.spigot.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
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
 * @since abril 05, 13:01
 */
class ModModeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this command.")
            return true
        }

        if (!sender.hasPermission(STAFF_PERMISSION)) {
            sender.sendMessage(NO_PERMISSION_MESSAGE)
            return true
        }

        sender.sendMessage("${ChatColor.GOLD}Mod Mode: ${if (!sender.hasMetadata(STAFF_METADATA_KEY)) "${ChatColor.GREEN}Enabled" else "${ChatColor.RED}Disabled"}")
        Bukkit.getServer().pluginManager.callEvent(PlayerModEvent(sender))
        return true
    }
}