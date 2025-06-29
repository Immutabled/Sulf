package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.STAFF_MESSAGE
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.StaffRegistry
import git.immutabled.sulf.spigot.staff.items.DEFAULT_STAFF_ITEMS
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

/**
 * @organization Mides Projects
 * @author Inmutable
 * @since abril 06, 12:12
 */
class PlayerSwapHandItemsListener(
    private val registry: StaffRegistry
) : Listener {

    @EventHandler
    fun onItemSwap(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        if (!player.hasMetadata(STAFF_METADATA_KEY)) return

        val item = event.mainHandItem

        DEFAULT_STAFF_ITEMS.forEach { staffItem ->
            if (staffItem.getItem(player).isSimilar(item)) {
                player.sendMessage(STAFF_MESSAGE)
                event.isCancelled = true
                player.updateInventory()
                return
            }
        }
    }
}
