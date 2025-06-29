package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.VANISH_MESSAGE
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:58
 */
class EntityDamageByEntityListener : Listener {

    @EventHandler
    fun onPlayerAttack(event: EntityDamageByEntityEvent) {
        if (event.damager is Player && event.damager.hasMetadata(VANISH_METADATA_KEY)) {
            event.damager.sendMessage(VANISH_MESSAGE)
            event.isCancelled = true
        }
    }
}