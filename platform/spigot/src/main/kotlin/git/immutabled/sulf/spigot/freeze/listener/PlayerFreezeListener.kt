package git.immutabled.sulf.spigot.freeze.listener

import git.immutabled.sulf.api.events.freeze.PlayerFreezeEvent
import git.immutabled.sulf.spigot.FREEZE_METADATA_KEY
import git.immutabled.sulf.spigot.FREEZE_PID
import git.immutabled.sulf.spigot.Plugin
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.metadata.FixedMetadataValue

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:47
 */
class PlayerFreezeListener(
    val plugin: Plugin
) : Listener {

    @EventHandler
    fun onFreeze(event: git.immutabled.sulf.api.events.freeze.PlayerFreezeEvent) {

        if (event.player.hasMetadata(FREEZE_METADATA_KEY)) {
            event.player.removeMetadata(FREEZE_METADATA_KEY, plugin)
            event.player.sendMessage("${ChatColor.GREEN}You have been unfrozen.")
        } else {
            event.player.setMetadata(FREEZE_METADATA_KEY, FixedMetadataValue(plugin, true))
            event.player.sendMessage("${ChatColor.RED}You have been frozen.")
        }

        plugin.getConnector().sendPacket(FREEZE_PID, mapOf(
            "id" to event.player.uniqueId.toString()
        ))

    }
}