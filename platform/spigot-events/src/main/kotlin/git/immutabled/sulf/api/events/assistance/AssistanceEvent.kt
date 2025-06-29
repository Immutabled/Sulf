package git.immutabled.sulf.api.events.assistance

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 15:45
 */
class AssistanceEvent(
    player: Player,
    val reason: String,
    val reported: Player? = null
) : PlayerEvent(player), Cancellable {

    private var cancel = false
    val isHelpOp: Boolean = reported == null

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }

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