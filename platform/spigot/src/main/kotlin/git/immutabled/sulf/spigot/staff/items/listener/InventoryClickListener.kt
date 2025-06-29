package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.STAFF_MESSAGE
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.StaffRegistry
import git.immutabled.sulf.spigot.staff.items.DEFAULT_STAFF_ITEMS
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 12:08
 */
class InventoryClickListener(
    private val registry: StaffRegistry
) : Listener {

    @EventHandler
    fun onItemMove(event: InventoryClickEvent) {
        val player = event.whoClicked

        if (player !is Player) return
        if (event.currentItem == null) return
        if (!player.hasMetadata(STAFF_METADATA_KEY)) return
        if (event.inventory.type != InventoryType.PLAYER) return


        DEFAULT_STAFF_ITEMS.forEach { item ->
            if (item.getSlot() == event.slot) {
                item.onClick(player, event.currentItem!!)
                player.sendMessage(STAFF_MESSAGE)
                event.isCancelled = true
                player.updateInventory()
            }
        }
    }
}