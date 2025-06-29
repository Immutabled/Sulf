package git.immutabled.connector.redis

import git.immutabled.connector.Connector
import git.immutabled.moshi.adapter.JsonObjectAdapter
import redis.clients.jedis.JedisPubSub
import java.lang.reflect.InvocationTargetException

class ConnectorPubSub(private val connector: Connector) : JedisPubSub() {

    override fun onMessage(channel: String,message: String) {

        if (channel != this.connector.channel) {
            return
        }

        val payload = try {
            JsonObjectAdapter.fromJson(message)
        } catch (ex: Exception) {
            throw IllegalStateException("Failed to handle message $message")
        }

        val from = payload.getInt("from") ?: throw IllegalStateException("Tried to handle packet but no from found.")
        val pid = payload.getString("pid") ?: throw IllegalStateException("Tried to handle packet but no pid found from $from.")

        val listeners = this.connector.listeners[pid.lowercase()]

        if (listeners.isNullOrEmpty()) {
            throw IllegalStateException("Tried to handle packet ${payload.getString("pid")} but no listeners found.")
        }

        val data = payload.getJsonObject("payload")

        for (listener in listeners.sortedBy{it.priority}) {

            if (!listener.forceLocal && from == this.connector.getPort()) {
                continue
            }

            try {
                listener.method.invoke(listener.instance,data)
            } catch (ex: InvocationTargetException) {
                continue
            }

        }

    }

}