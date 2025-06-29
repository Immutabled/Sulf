package git.immutabled.sulf.velocity.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import java.io.IOException

object ComponentAdapter {

    @FromJson
    fun fromJson(json: String): Component? {
        return try {
            GsonComponentSerializer.gson().deserialize(json)
        } catch (e: Exception) {
            throw IOException("Failed to parse Kyori Component", e)
        }
    }

    @ToJson
    fun toJson(component: Component): String {
        return GsonComponentSerializer.gson().serialize(component)
    }
}