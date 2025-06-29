package git.immutabled.moshi

import com.squareup.moshi.JsonClass
import java.io.File

@JsonClass(generateAdapter = true)
open class JsonConfig {

    @Transient lateinit var file: File

}