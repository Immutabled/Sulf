package git.immutabled.sulf.spigot.staff.items.event

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 11:56
 */
class RestoreInventoryEvent(
    player: Player,
) : PlayerEvent(player) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()
    }

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}