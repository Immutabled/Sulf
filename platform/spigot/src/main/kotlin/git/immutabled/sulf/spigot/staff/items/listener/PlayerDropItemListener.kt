package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.STAFF_MESSAGE
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.items.DEFAULT_STAFF_ITEMS
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

/**
 * @organization Mides Projects
 * @author Inmutable
 * @since abril 06, 12:12
 */
class PlayerDropItemListener : Listener {

    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val dropped = event.itemDrop.itemStack

        if (!player.hasMetadata(STAFF_METADATA_KEY)) return

        if (DEFAULT_STAFF_ITEMS.any { it.getItem(player).isSimilar(dropped) }) {
            player.sendMessage(STAFF_MESSAGE)
            event.isCancelled = true
            player.updateInventory()
        }
    }
}
