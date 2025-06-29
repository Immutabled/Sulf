package git.immutabled.moshi.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import git.immutabled.moshi.annotation.SerializeNull
import java.lang.reflect.Type

object SerializeNullAdapter : JsonAdapter.Factory {

    override fun create(type: Type,annotations: MutableSet<out Annotation>,moshi: Moshi): JsonAdapter<Any>? {

        val annotation = Types.nextAnnotations(annotations, SerializeNull::class.java)

        if (annotation == null || annotation.isEmpty()) {
            return null
        }

        return moshi.nextAdapter<Any>(this,type,annotation).serializeNulls()
    }

}