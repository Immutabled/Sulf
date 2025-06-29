package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.items.DEFAULT_STAFF_ITEMS
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * @organization Mides Projects
 * @author Inmutable
 * @since abril 06, 12:15
 */
class PlayerInteractEntityListener : Listener {

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        if (!player.hasMetadata(STAFF_METADATA_KEY)) return
        if (event.hand != EquipmentSlot.HAND) return

        val item = player.inventory.itemInMainHand
        if (item.type.isAir) return

        DEFAULT_STAFF_ITEMS.forEach { staffItem ->
            if (staffItem.getItem(player).isSimilar(item)) {
                staffItem.onEntityInteract(event)
                event.isCancelled = true
                player.updateInventory()
                return
            }
        }
    }
}
