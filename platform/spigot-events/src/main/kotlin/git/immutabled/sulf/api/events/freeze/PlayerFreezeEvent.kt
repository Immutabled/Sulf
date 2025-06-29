package git.immutabled.sulf.api.events.freeze

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:47
 */
class PlayerFreezeEvent(
    player: Player
) : PlayerEvent(player) {

    override fun getHandlers(): HandlerList {
        return git.immutabled.sulf.api.events.freeze.PlayerFreezeEvent.Companion.getHandlerList()
    }

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return git.immutabled.sulf.api.events.freeze.PlayerFreezeEvent.Companion.handlers
        }
    }
}