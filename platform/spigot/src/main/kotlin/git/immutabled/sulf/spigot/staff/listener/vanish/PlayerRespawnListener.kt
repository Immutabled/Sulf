package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player

        if (!player.hasMetadata(VANISH_METADATA_KEY)) return

        plugin.server.onlinePlayers
            .filter { it != player && !it.hasPermission("sulf.staff.vanish.see") }
            .forEach { it.hidePlayer(plugin, player) }
    }
}
