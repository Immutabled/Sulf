package git.immutabled.sulf.spigot.staff.listener.mod

import git.immutabled.sulf.api.events.staff.logging.StaffLeaveEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.staff.StaffRegistry
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:22
 */
class PlayerQuitListener(
    private val plugin: Plugin,
    private val staffRegistry: StaffRegistry
): Listener {

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {

        //disconnect from the server
        if (!this.staffRegistry.online.contains(event.player.uniqueId)) {
            // Player is already staff
            return
        }


        Bukkit.getServer().pluginManager.callEvent(StaffLeaveEvent(event.player))
        this.staffRegistry.online.remove(event.player.uniqueId)
    }
}