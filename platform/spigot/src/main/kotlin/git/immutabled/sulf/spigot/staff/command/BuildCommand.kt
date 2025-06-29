package git.immutabled.sulf.spigot.staff.command

import net.md_5.bungee.api.ChatColor
import git.immutabled.sulf.api.events.staff.PlayerBuildEvent
import git.immutabled.sulf.spigot.BUILD_METADATA_KEY
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
 * @since abril 05, 14:02
 */
class BuildCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this command.")
            return true
        }

        if (!sender.hasPermission(STAFF_PERMISSION)) {
            sender.sendMessage(NO_PERMISSION_MESSAGE)
            return true
        }

        sender.sendMessage("${ChatColor.GOLD}Build: ${if (!sender.hasMetadata(BUILD_METADATA_KEY)) "${ChatColor.GREEN}Enabled" else "${ChatColor.RED}Disabled"}")
        Bukkit.getServer().pluginManager.callEvent(PlayerBuildEvent(sender))
        return true
    }
}