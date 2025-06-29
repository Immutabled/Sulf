package git.immutabled.sulf.spigot.staff.items.listener

import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 12:17
 */
class PlayerPickupItemListener : Listener {

    @EventHandler
    fun onPickupItem(event: PlayerPickupItemEvent) {
        if (!event.player.hasMetadata(STAFF_METADATA_KEY)) {
            return
        }

        event.isCancelled = true
    }
}