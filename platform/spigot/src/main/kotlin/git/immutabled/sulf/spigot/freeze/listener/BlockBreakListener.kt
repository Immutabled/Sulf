package git.immutabled.sulf.spigot.freeze.listener

import git.immutabled.sulf.spigot.FREEZE_MESSAGE
import git.immutabled.sulf.spigot.FREEZE_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:20
 */
class BlockBreakListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.hasMetadata(FREEZE_METADATA_KEY)) {
            event.player.sendMessage(FREEZE_MESSAGE)
            event.isCancelled = true
        }
    }
}