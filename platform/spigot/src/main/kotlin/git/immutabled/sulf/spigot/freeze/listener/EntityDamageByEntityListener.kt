package git.immutabled.sulf.spigot.freeze.listener

import git.immutabled.sulf.spigot.FREEZE_MESSAGE
import git.immutabled.sulf.spigot.FREEZE_METADATA_KEY
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:20
 */
class EntityDamageByEntityListener : Listener {

    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        if (event.damager is Player && event.damager.hasMetadata(FREEZE_METADATA_KEY)) {
            event.damager.sendMessage(FREEZE_MESSAGE)
            event.isCancelled = true
        }
    }
}