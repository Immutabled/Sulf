package git.immutabled.moshi.adapter

import com.squareup.moshi.*
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class ConcurrentMapJsonAdapter<K, V>(
    private val keyAdapter: JsonAdapter<K>,
    private val valueAdapter: JsonAdapter<V>
) : JsonAdapter<ConcurrentHashMap<K, V>>() {

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, map: ConcurrentHashMap<K, V>?) {
        writer.beginObject()
        for ((key, value) in map ?: emptyMap()) {
            if (key == null) {
                throw JsonDataException("Map key is null at ${writer.path}")
            }
            keyAdapter.toJson(writer, key)
            valueAdapter.toJson(writer, value)
        }
        writer.endObject()
    }

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): ConcurrentHashMap<K, V>? {
        val result = ConcurrentHashMap<K, V>()
        reader.beginObject()
        while (reader.hasNext()) {
            val name = keyAdapter.fromJson(reader.nextName())
            val value = valueAdapter.fromJson(reader)
            val replaced = result.put(name!!, value!!)
            if (replaced != null) {
                throw JsonDataException("Map key '$name' has multiple values at path ${reader.path} : $replaced and value")
            }
        }
        reader.endObject()
        return result
    }

    override fun toString(): String = "JsonAdapter($keyAdapter=$valueAdapter)"

    companion object
}