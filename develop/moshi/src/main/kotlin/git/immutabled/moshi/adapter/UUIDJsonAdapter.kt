package git.immutabled.moshi.adapter

import java.util.*
import com.squareup.moshi.*

object UUIDJsonAdapter {

    private val REGEX = Regex("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})")

    @ToJson
    fun toJson(uuid: UUID):String {
        return uuid.toString()
    }

    @FromJson
    fun fromJson(json: String):UUID {

        if (json.length == 36) {
            UUID.fromString(json)
        }

        return UUID.fromString(REGEX.replaceFirst(json,"$1-$2-$3-$4-$5"))
    }

}