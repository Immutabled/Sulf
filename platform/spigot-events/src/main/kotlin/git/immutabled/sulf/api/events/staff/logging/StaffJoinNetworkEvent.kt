package git.immutabled.sulf.api.events.staff.logging

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 16:54
 */
class StaffJoinNetworkEvent(
    player: Player,
    val server: String
) : PlayerEvent(player) {

    override fun getHandlers(): org.bukkit.event.HandlerList {
        return getHandlerList()
    }

    companion object {
        private val handlers = org.bukkit.event.HandlerList()

        @JvmStatic
        fun getHandlerList(): org.bukkit.event.HandlerList {
            return handlers
        }
    }
}