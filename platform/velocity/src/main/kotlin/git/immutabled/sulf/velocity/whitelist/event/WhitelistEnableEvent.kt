package git.immutabled.sulf.velocity.whitelist.event

import com.velocitypowered.api.event.ResultedEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:50
 */
class WhitelistEnableEvent : ResultedEvent<ResultedEvent.GenericResult> {

    private var result: ResultedEvent.GenericResult = ResultedEvent.GenericResult.allowed()

    override fun getResult(): ResultedEvent.GenericResult = result

    override fun setResult(result: ResultedEvent.GenericResult) {
        this.result = result
    }

    fun isCancelled(): Boolean = result.isAllowed
}