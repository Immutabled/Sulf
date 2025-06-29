package git.immutabled.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import git.immutabled.moshi.MoshiUtil
import git.immutabled.moshi.json.JsonObject

object JsonObjectAdapter {

    @ToJson
    fun toJson(json: JsonObject):String {
        return MoshiUtil.instance.adapter<Map<String,Any?>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE).toJson(json.get())
    }

    @FromJson
    fun fromJson(json: String): JsonObject {
        return JsonObject(MoshiUtil.instance.adapter<Map<String,Any?>>(MoshiUtil.STRING_TO_ANY_MAP_TYPE).fromJson(json)!!,raw = json)
    }

}