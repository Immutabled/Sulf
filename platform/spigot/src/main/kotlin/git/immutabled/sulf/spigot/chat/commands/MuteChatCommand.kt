package git.immutabled.sulf.spigot.chat.commands

import git.immutabled.sulf.spigot.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import git.immutabled.sulf.spigot.chat.ChatModule
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
 * @since abril 06, 18:01
 */
class MuteChatCommand(
    private val module: ChatModule
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (!sender.hasPermission(STAFF_PERMISSION)) {
            sender.sendMessage(NO_PERMISSION_MESSAGE)
            return true
        }
        module.muted = !module.muted

        var message = "${ChatColor.LIGHT_PURPLE}Public chat has been ${if (module.muted) "" else "un"}muted"
        val staffMessage = "$message by ${if (sender is Player) {
            ChatColor.translateAlternateColorCodes('&', Plugin.instance.coreHook.getDisplayNameString(sender, false))
        } else {"${ChatColor.DARK_RED}Console"}}${ChatColor.LIGHT_PURPLE}."

        message += "."

        Bukkit.getServer().onlinePlayers.forEach{

            if (it.hasPermission(STAFF_PERMISSION)) {
                it.sendMessage(staffMessage)
            } else {
                it.sendMessage(message)
            }
        }
        return true
    }

}