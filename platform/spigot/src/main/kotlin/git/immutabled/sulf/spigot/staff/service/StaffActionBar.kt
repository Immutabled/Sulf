package git.immutabled.sulf.spigot.staff.service

import git.immutabled.sulf.spigot.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 19:53
 */
class StaffActionBar(
    private val plugin: Plugin
) : BukkitRunnable() {

    override fun run() {
        val tps = plugin.server.tps[0]
        val formattedTps = when {
            tps > 18.0 -> "${ChatColor.GREEN}%.1f".format(tps)
            tps > 15.0 -> "${ChatColor.YELLOW}%.1f".format(tps)
            else -> "${ChatColor.RED}%.1f".format(tps)
        }

        plugin.server.onlinePlayers.filter { player ->
            player.hasMetadata(STAFF_METADATA_KEY)
        }.forEach { player ->
            val vanishStatus = if (player.hasMetadata(VANISH_METADATA_KEY)) "${ChatColor.GREEN}Enabled" else "${ChatColor.RED}Disabled"
            val buildStatus = if (player.hasMetadata(BUILD_METADATA_KEY)) "${ChatColor.GREEN}Enabled" else "${ChatColor.RED}Disabled"
            val staffchannel = if (player.hasMetadata(STAFF_CHAT_METADATA_KEY)) "${ChatColor.AQUA}Staff" else "${ChatColor.GRAY}Default"

            val actionBarMessage = "${ChatColor.LIGHT_PURPLE}Channel${ChatColor.GRAY}: $staffchannel ${ChatColor.WHITE}| ${ChatColor.LIGHT_PURPLE}Vanish${ChatColor.GRAY}: $vanishStatus ${ChatColor.WHITE}| ${ChatColor.LIGHT_PURPLE}Build${ChatColor.GRAY}: $buildStatus ${ChatColor.WHITE}| ${ChatColor.LIGHT_PURPLE}TPS${ChatColor.GRAY}: $formattedTps ${ChatColor.WHITE}| ${ChatColor.LIGHT_PURPLE}Players${ChatColor.GRAY}: ${ChatColor.WHITE}${this.plugin.players}"

            player.sendActionBar(actionBarMessage)
        }
    }
}