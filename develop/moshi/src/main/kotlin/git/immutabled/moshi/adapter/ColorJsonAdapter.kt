package git.immutabled.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.awt.Color


object ColorJsonAdapter {

    @ToJson
    fun toJson(color: Color): String {
        return "#${Integer.toHexString(color.rgb).substring(2)}"
    }


    @FromJson
    fun fromJson(colorString: String): Color {
        return Color.decode(colorString)
    }
}