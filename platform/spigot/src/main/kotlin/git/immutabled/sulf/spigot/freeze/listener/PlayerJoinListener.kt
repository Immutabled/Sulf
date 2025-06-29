package git.immutabled.sulf.spigot.freeze.listener

import git.immutabled.sulf.api.events.staff.PlayerVanishEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.freeze.FreezeRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:31
 */
class PlayerJoinListener(
    private val plugin: Plugin,
    private val registry: FreezeRegistry
) : Listener{

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!registry.freezes.contains(event.player.uniqueId)) {
            return
        }

        this.plugin.server.pluginManager.callEvent(PlayerVanishEvent(event.player))
    }
}