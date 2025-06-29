package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.api.events.staff.PlayerVanishEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.metadata.FixedMetadataValue

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:36
 */
class PlayerVanishListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onPlayerVanish(event: PlayerVanishEvent) {
        val player = event.player
        val force = event.force

        val vanished = player.hasMetadata(VANISH_METADATA_KEY)

        // Determine whether we want to vanish or unvanish
        val shouldVanish = when (force) {
            true -> false  // force = true → unvanish
            false -> true  // force = false → vanish
            null -> !vanished // toggle
        }

        if (shouldVanish) {
            plugin.server.onlinePlayers
                .filter { it != player && !it.hasPermission("sulf.staff.vanish.see") }
                .forEach { it.hidePlayer(plugin, player) }

            player.setMetadata(VANISH_METADATA_KEY, FixedMetadataValue(plugin, true))
        } else {
            player.removeMetadata(VANISH_METADATA_KEY, plugin)
            plugin.server.onlinePlayers.forEach { it.showPlayer(plugin, player) }
        }
    }

}