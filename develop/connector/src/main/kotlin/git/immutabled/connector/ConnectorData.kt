package git.immutabled.connector

import java.lang.reflect.Method

data class ConnectorData(val id: String, val method: Method, val instance: Any, val priority: Int = 4, val forceLocal: Boolean)