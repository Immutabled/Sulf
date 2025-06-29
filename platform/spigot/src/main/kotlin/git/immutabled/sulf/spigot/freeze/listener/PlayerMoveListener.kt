package git.immutabled.sulf.spigot.freeze.listener

import git.immutabled.sulf.spigot.FREEZE_METADATA_KEY
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:19
 */
class PlayerMoveListener : Listener {

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if (event.player.hasMetadata(FREEZE_METADATA_KEY)) {
                if (event.from.x != event.to.x || event.from.z != event.to.z) {
                    event.player.sendMessage("${ChatColor.RED}You cannot move while frozen.")
                    event.player.teleport(event.from)
                }
            }
    }
}
