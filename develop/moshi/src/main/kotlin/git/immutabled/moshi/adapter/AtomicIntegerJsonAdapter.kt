package git.immutabled.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.util.concurrent.atomic.AtomicInteger


object AtomicIntegerJsonAdapter {

    @ToJson
    fun toJson(value: AtomicInteger):Int {
        return value.get()
    }

    @FromJson
    fun fromJson(value: Int):AtomicInteger {
        return AtomicInteger(value)
    }

}