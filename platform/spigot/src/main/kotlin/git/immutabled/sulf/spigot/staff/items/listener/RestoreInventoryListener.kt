package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.staff.StaffRegistry
import git.immutabled.sulf.spigot.staff.items.event.RestoreInventoryEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 12:01
 */
class RestoreInventoryListener(
    private val registry: StaffRegistry
) : Listener {

    @EventHandler
    fun onRestoreInventory(event: RestoreInventoryEvent) {
        val player = event.player
        val savedInventory = registry.inventory[player.uniqueId] ?: return

        player.inventory.clear()

        savedInventory.forEachIndexed { index, item ->
            if (item != null) {
                player.inventory.setItem(index, item)
            }
        }
    }
}