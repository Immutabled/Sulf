package git.immutabled.sulf.velocity.proxy.event

import com.velocitypowered.api.event.ResultedEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:27
 */
class ProxyUpdateEvent (
 val key: String,
 val value: Any
) : ResultedEvent<ResultedEvent.GenericResult> {

 private var result: ResultedEvent.GenericResult = ResultedEvent.GenericResult.allowed()

 override fun getResult(): ResultedEvent.GenericResult = result

 override fun setResult(result: ResultedEvent.GenericResult) {
  this.result = result
 }

 fun isCancelled(): Boolean = result.isAllowed
}