package git.immutabled.sulf.spigot.staff.listener.mod

import git.immutabled.sulf.api.events.staff.PlayerModEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.staff.items.DEFAULT_STAFF_ITEMS
import git.immutabled.sulf.spigot.staff.items.event.RestoreInventoryEvent
import git.immutabled.sulf.spigot.staff.items.event.SetItemsEvent
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.metadata.FixedMetadataValue

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:05
 */
class PlayerModListener(
    private val plugin: Plugin
): Listener {

  @EventHandler
  fun onModMode(event: PlayerModEvent) {
    if (event.player.hasMetadata(STAFF_METADATA_KEY)) {
      event.player.removeMetadata(STAFF_METADATA_KEY, plugin)
      plugin.server.pluginManager.callEvent(RestoreInventoryEvent(event.player))
    } else {
      event.player.setMetadata(STAFF_METADATA_KEY, FixedMetadataValue(plugin, true))
      plugin.server.pluginManager.callEvent(SetItemsEvent(event.player, DEFAULT_STAFF_ITEMS))
    }

    event.player.gameMode = GameMode.CREATIVE
  }

}