package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.BUILD_METADATA_KEY
import git.immutabled.sulf.spigot.VANISH_MESSAGE
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:59
 */
class VanishBlockListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.hasMetadata(VANISH_METADATA_KEY)) {

            if (event.player.hasMetadata(BUILD_METADATA_KEY)) return

            event.player.sendMessage(VANISH_MESSAGE)
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.player.hasMetadata(VANISH_METADATA_KEY)) {

            if (event.player.hasMetadata(BUILD_METADATA_KEY)) return

            event.player.sendMessage(VANISH_MESSAGE)
            event.isCancelled = true
        }
    }
}