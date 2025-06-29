package git.immutabled.connector

import git.immutabled.connector.cross.CROSS_PID
import git.immutabled.connector.listener.PacketListener
import git.immutabled.connector.redis.ConnectorPubSub
import git.immutabled.moshi.MoshiUtil
import git.immutabled.moshi.json.JsonObject

import redis.clients.jedis.JedisPool
import redis.clients.jedis.Pipeline
import java.lang.Exception
import java.util.concurrent.Executors
import kotlin.jvm.Throws

class Connector(var channel: String, var pool: JedisPool = JedisPool(), private var port: Int = -1) {

    val pubSub = ConnectorPubSub(this)
    val listeners = hashMapOf<String,MutableList<ConnectorData>>()

    private val executor = Executors.newCachedThreadPool()

    init {
        instance = this
        this.executor.execute{this.pool.resource.use{it.subscribe(this.pubSub,this.channel)}}
    }

    fun getPort():Int {
        return this.port
    }

    fun setPort(port: Int): Connector {
        this.port = port
        return this
    }

    @Throws(Exception::class)
    fun sendPacket(id: String,json: String) {
        this.executor.execute{this.sendPacketSync(id,json)}
    }

    @Throws(Exception::class)
    fun sendPacketSync(id: String,json: String) {
        this.pool.resource.use{it.publish(this.channel,
            MoshiUtil.instance.adapter<Map<String,Any>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE.rawType).toJson(mapOf(
            "pid" to id,
            "from" to this.port,
            "payload" to json
        )))}
    }

    @Throws(Exception::class)
    fun sendACross(body: Map<String,Any>) {
        this.executor.execute{this.sendPacketSync(CROSS_PID,body)}
    }

    @Throws(Exception::class)
    fun sendPacket(id: String,body: Map<String,Any>) {
        this.executor.execute{this.sendPacketSync(id,body)}
    }

    @Throws(Exception::class)
    fun <T> sendPacket(id: String,body: T) {
        this.executor.execute { this.sendPacketSync(this.channel,body) }
    }

    @Throws(Exception::class)
    fun <T> sendPacketSync(id: String,body: T) {

        val payload = JsonObject()

        payload["pid"] = id
        payload["from"] = this.port
        payload["payload"] = body!!

        this.pool.resource.use{it.publish(this.channel,payload.toJson(MoshiUtil.instance))}
    }

    @Throws(Exception::class)
    fun sendPacketSync(id: String,body: Map<String,Any>) {
        this.sendPacketSync(id, MoshiUtil.instance.adapter<Map<String,Any>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE).toJson(body))
    }

    fun <T> addToPipeline(id: String,body: T,pipeline: Pipeline) {

        val payload = JsonObject()

        payload["pid"] = id
        payload["from"] = this.port
        payload["payload"] = body!!

        pipeline.publish(this.channel,payload.toJson(MoshiUtil.instance))
    }

    fun addToPipeline(id: String, body: Map<String, Any>, pipeline: Pipeline) {
        pipeline.publish(this.channel,MoshiUtil.instance.adapter<Map<String,Any>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE.rawType).toJson(mapOf(
            "pid" to id,
            "from" to this.port,
            "payload" to MoshiUtil.instance.adapter<Map<String,Any>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE).toJson(body)
        )))
    }

    fun addToPipeline(id: String,body: JsonObject,pipeline: Pipeline) {

        val payload = JsonObject()

        payload["pid"] = id
        payload["from"] = this.port
        payload["payload"] = body.toJson(MoshiUtil.instance)

        pipeline.publish(this.channel,payload.toJson(MoshiUtil.instance))
    }

    fun addListener(listener: PacketListener) {

        listener.javaClass.declaredMethods.forEach{

            val annotation = it.getDeclaredAnnotation(Data::class.java)

            if (annotation == null || !JsonObject::class.java.isAssignableFrom(it.parameterTypes.first())) {
                return@forEach
            }

            val data = ConnectorData(annotation.id,it,listener,annotation.priority,annotation.forceLocal)

            this.listeners.getOrPut(annotation.id.lowercase()) { mutableListOf() }.add(data)
        }

    }

    companion object {

        lateinit var instance: Connector

    }

}