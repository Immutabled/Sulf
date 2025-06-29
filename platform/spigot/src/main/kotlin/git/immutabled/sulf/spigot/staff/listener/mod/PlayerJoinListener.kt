package git.immutabled.sulf.spigot.staff.listener.mod;

import git.immutabled.sulf.api.events.staff.logging.StaffJoinNetworkEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import git.immutabled.sulf.spigot.staff.StaffRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:21
 */
class PlayerJoinListener(
    private val plugin: Plugin,
    private val staffRegistry: StaffRegistry
) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        if (!event.player.hasPermission(STAFF_PERMISSION)) {
            return
        }

        if (staffRegistry.online.contains(event.player.uniqueId)) {
            // Player is already staff
            plugin.bridge!!.getPlayerServer(event.player).thenAccept { server ->
                server.takeIf { it != "unknown" } ?: return@thenAccept
                staffRegistry.online[event.player.uniqueId] = server
                plugin.server.pluginManager.callEvent(
                    StaffJoinNetworkEvent(
                        event.player,
                        server
                    )
                )
            }
            return
        }

        plugin.bridge!!.getPlayerServer(event.player).thenAccept { server ->
            server.takeIf { it != "unknown" } ?: return@thenAccept
            staffRegistry.online[event.player.uniqueId] = server
            plugin.server.pluginManager.callEvent(
                StaffJoinNetworkEvent(
                    event.player,
                    server
                )
            )
        }
    }

}
