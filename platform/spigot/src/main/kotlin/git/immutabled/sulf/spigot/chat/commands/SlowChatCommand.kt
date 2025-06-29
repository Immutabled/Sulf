package git.immutabled.sulf.spigot.chat.commands

import git.immutabled.sulf.spigot.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_MESSAGE
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import git.immutabled.sulf.spigot.chat.ChatModule
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 18:01
 */
class SlowChatCommand(
    private val module: ChatModule
) : CommandExecutor {

    private val timePattern = Pattern.compile("^(\\d+)([smhd]?)$", Pattern.CASE_INSENSITIVE)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {


        if (!sender.hasPermission(STAFF_PERMISSION)) {
            sender.sendMessage(NO_PERMISSION_MESSAGE)
            return true
        }


        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}Usage: /slowchat <duration>")
            return true
        }

        val timeInput = args[0].lowercase()
        val matcher = timePattern.matcher(timeInput)

        if (!matcher.matches()) {
            sender.sendMessage("${ChatColor.RED}Invalid time format. Use: 10s, 5m, 2h, 1d")
            return true
        }

        val amount = matcher.group(1).toLongOrNull() ?: 0L
        val unit = matcher.group(2).lowercase()

        if (amount <= 0) {
            sender.sendMessage("${ChatColor.RED}Time amount must be positive")
            return true
        }

        val duration = when (unit) {
            "s" -> TimeUnit.SECONDS.toMillis(amount)
            "m" -> TimeUnit.MINUTES.toMillis(amount)
            "h" -> TimeUnit.HOURS.toMillis(amount)
            "d" -> TimeUnit.DAYS.toMillis(amount)
            else -> amount * 1000 // Default to seconds if no unit specified
        }

        if (duration == 0L) {
            // Toggle slow mode
            if (this.module.slowed) {
                module.slowTime = 0L
                module.slowed = false
            } else {
                module.slowTime = module.defaultSlowTime
                module.slowed = true
            }
        } else {
            module.slowTime = duration
            module.slowed = true
        }

        val isNowSlowed = module.slowed
        var message = "${ChatColor.LIGHT_PURPLE}Public chat has been ${if (isNowSlowed) "slowed" else "unslowed"}"
        val staffMessage = "$message by ${if (sender is Player)
            ChatColor.translateAlternateColorCodes('&', Plugin.instance.coreHook.getDisplayNameString(sender, false))
        else "Console"}${ChatColor.LIGHT_PURPLE}."

        message += "."

        Bukkit.getServer().onlinePlayers.forEach { player ->
            if (player.hasPermission(STAFF_MESSAGE)) {
                player.sendMessage(staffMessage)
            } else {
                player.sendMessage(message)
            }
        }

        return true
    }
}