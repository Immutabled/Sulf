package git.immutabled.sulf.spigot.assistance.listener;

import git.immutabled.sulf.spigot.assistance.AssistanceRegistry
import git.immutabled.sulf.api.events.assistance.AssistanceEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 10:48
 */
class AssistanceCooldownListener(private val registry: AssistanceRegistry) : Listener {

    @EventHandler(ignoreCancelled = false)
    fun onAssistanceEvent(event: AssistanceEvent) {
        if (this.registry.hasCooldown(event.player)) {
            val remaining = registry.getCooldown(event.player)
            val minutes = remaining / 60000
            val seconds = (remaining % 60000) / 1000

            event.player.sendMessage("${org.bukkit.ChatColor.RED}You must wait $minutes minutes and $seconds seconds before using this again.")
            event.isCancelled = true
        }
    }
}
