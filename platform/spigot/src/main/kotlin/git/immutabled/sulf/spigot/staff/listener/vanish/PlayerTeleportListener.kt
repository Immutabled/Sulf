package git.immutabled.sulf.spigot.staff.listener.vanish

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class PlayerTeleportListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onTeleport(event: PlayerTeleportEvent) {
        val player = event.player

        if (!player.hasMetadata(VANISH_METADATA_KEY)) return

        plugin.server.onlinePlayers
            .filter { it != player && !it.hasPermission("sulf.staff.vanish.see") }
            .forEach { it.hidePlayer(plugin, player) }
    }
}
