package git.immutabled.sulf.velocity.staff.event

import com.velocitypowered.api.event.ResultedEvent
import net.kyori.adventure.text.Component

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:29
 */
class StaffAlertEvent(
    val message: Component
) : ResultedEvent<ResultedEvent.GenericResult> {

    private var result: ResultedEvent.GenericResult = ResultedEvent.GenericResult.allowed()

    override fun getResult(): ResultedEvent.GenericResult = result

    override fun setResult(result: ResultedEvent.GenericResult) {
        this.result = result
    }

    fun isCancelled(): Boolean = result.isAllowed
}