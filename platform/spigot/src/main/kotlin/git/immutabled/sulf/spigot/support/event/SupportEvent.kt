package git.immutabled.sulf.spigot.support.event

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 13, 23:58
 */
class SupportEvent(
    player: Player,
    val partner: String
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