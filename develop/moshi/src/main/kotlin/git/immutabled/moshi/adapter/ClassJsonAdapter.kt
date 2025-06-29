package git.immutabled.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import git.immutabled.moshi.annotation.ClassSerializer

object ClassJsonAdapter {

    @ToJson
    fun toJson(@ClassSerializer clazz: Class<*>):String {
        return clazz.canonicalName
    }

    @FromJson
    @ClassSerializer
    fun fromJson(path: String):Class<*> {
        return Class.forName(path)
    }

}