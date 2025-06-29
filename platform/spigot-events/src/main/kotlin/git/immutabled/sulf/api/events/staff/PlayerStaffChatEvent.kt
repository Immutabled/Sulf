package git.immutabled.sulf.api.events.staff

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 15:44
 */
class PlayerStaffChatEvent(
    player: Player,
    val message: String
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