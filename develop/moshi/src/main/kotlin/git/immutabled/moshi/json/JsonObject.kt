package git.immutabled.moshi.json

import com.squareup.moshi.Moshi
import git.immutabled.moshi.MoshiUtil

class JsonObject {

    private var json = "{}"
    private var jsonUpdateRequired = true
    private var lastJsonUpdateNulls = false

    constructor() {
        this.entries = mutableMapOf()
    }

    constructor(entries: Map<*,*>,raw: String? = null) {

        if (raw != null) {
            this.json = raw
        }

        val newEntries = mutableMapOf<String,Any?>()

        for (entry in entries) {

            if (entry.key !is String) {
                continue
            }

            newEntries[entry.key as String] = entry.value
        }

        this.entries = newEntries
    }

    private var entries: MutableMap<String,Any?>

    fun get():Map<String,Any?> {
        return this.entries
    }

    operator fun get(key: String): Any? {
        return this.entries[key]
    }

    operator fun set(key: String,value: Any) {
        this.entries[key] = value
        this.jsonUpdateRequired = true
    }

    fun containsKey(key: String):Boolean {
        return this.entries.containsKey(key)
    }

    fun getInt(key: String):Int? {

        val value = this.entries[key]

        if (value is Double) {
            return value.toInt()
        }

        return value as? Int
    }

    fun getLong(key: String):Long? {

        val value = this.entries[key]

        if (value is Double) {
            return value.toLong()
        }

        return value as? Long
    }

    fun getFloat(key: String):Float? {
        return this.entries[key] as? Float
    }

    fun getString(key: String):String? {
        return this.entries[key] as? String
    }

    fun getDouble(key: String):Double? {
        return this.entries[key] as? Double
    }

    fun getBoolean(key: String):Boolean? {
        return this.entries[key] as? Boolean
    }

    fun <T> getAs(type: Class<out T>):T? {
        return MoshiUtil.instance.adapter<T>(type).fromJson(this.toJson(MoshiUtil.instance))
    }

    fun <T> getAs(key: String,type: Class<out T>):T? {
        return MoshiUtil.instance.adapter<T>(type).fromJson(this.entries[key] as String)
    }

    fun getJsonObject(key: String): JsonObject {
        val value = this.entries[key]
        return when {
            value is Map<*,*> -> JsonObject(value)
            value is String && value.isNotEmpty() && value[0] == '{' && value[value.lastIndex] == '}' -> {

                val fromJson = fromJson(value)

                // re-update value in cache to a Map so we don't have to deserialize again.
                this.entries[key] = fromJson.get()

                fromJson
            }
            else -> JsonObject()
        }

    }

    fun toJson(moshi: Moshi,serializeNulls: Boolean = false):String {

        if (this.lastJsonUpdateNulls != serializeNulls) {
            this.lastJsonUpdateNulls = serializeNulls
            this.jsonUpdateRequired = true
        }

        if (this.jsonUpdateRequired) {

            var adapter = moshi.adapter<Map<String,Any?>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE)

            if (serializeNulls) {
                adapter = adapter.serializeNulls()
            }

            this.json = adapter.toJson(this.entries)!!
        }

        return this.json
    }

    companion object {

        fun fromJson(value: String): JsonObject {
            return JsonObject(MoshiUtil.instance.adapter<Map<String,Any?>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE).fromJson(value)!!)
        }

    }
}