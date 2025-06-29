package git.immutabled.sulf.api.events.staff.logging

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 17:04
 */
class StaffLeaveEvent(
    player: Player
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