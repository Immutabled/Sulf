package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.STAFF_MESSAGE
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.items.DEFAULT_STAFF_ITEMS
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * @organization Mides Projects
 * @author Inmutable
 * @since abril 06, 12:16
 */
class PlayerInteractListener : Listener {

    @EventHandler (ignoreCancelled = true)
    fun onItemUse(event: PlayerInteractEvent) {
        val player = event.player

        if (!player.hasMetadata(STAFF_METADATA_KEY)) return
        if (event.hand != EquipmentSlot.HAND) return
        val item = event.item ?: return

        DEFAULT_STAFF_ITEMS.forEach { staffItem ->
            if (staffItem.getSlot() == player.inventory.heldItemSlot) {
                staffItem.onClick(player, item)
                event.isCancelled = true
                player.updateInventory()
                return
            }
        }
    }
}
