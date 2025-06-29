package git.immutabled.sulf.api.events.staff;

import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 17:21
 */
class StaffAlertEvent(
    val component: Component
) : Event() {
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
