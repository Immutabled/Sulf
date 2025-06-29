package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val joining = event.player

        plugin.server.onlinePlayers
            .filter { it != joining && it.hasMetadata(VANISH_METADATA_KEY) }
            .forEach { vanished ->
                if (!joining.hasPermission("sulf.staff.vanish.see")) {
                    joining.hidePlayer(plugin, vanished)
                }
            }
    }
}
