package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.VANISH_MESSAGE
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:00
 */
class PlayerInteractEntityListener : Listener{

    @EventHandler
    fun onEntityInteract(event: PlayerInteractEntityEvent) {
        if (event.player.hasMetadata(VANISH_METADATA_KEY)) {
            event.player.sendMessage(VANISH_MESSAGE)
            event.isCancelled = true
        }
    }
}