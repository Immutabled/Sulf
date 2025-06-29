package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.StaffRegistry
import git.immutabled.sulf.spigot.staff.items.event.SetItemsEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 11:59
 */
class SetItemsListener(
    private val registry: StaffRegistry
) : Listener {

    @EventHandler
    fun onSetItems(event: SetItemsEvent) {

        if (!event.player.hasMetadata(STAFF_METADATA_KEY)) {
            // Player is not staff so we don't need to do anything
            return
        }

        registry.inventory[event.player.uniqueId] = event.player.inventory.contents

        event.player.inventory.clear()
        event.items.forEach {
            event.player.inventory.setItem(it.getSlot(), it.getItem(event.player))
        }
    }
}