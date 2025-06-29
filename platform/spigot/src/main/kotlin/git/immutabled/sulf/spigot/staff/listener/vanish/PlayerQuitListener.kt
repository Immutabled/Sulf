package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        event.player.removeMetadata(VANISH_METADATA_KEY, plugin)
    }
}
