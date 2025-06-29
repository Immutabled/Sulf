package git.immutabled.sulf.velocity.template

import com.squareup.moshi.JsonClass

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:08
 */
@JsonClass(generateAdapter = true)
data class Template(
    var name: String,
    var entry: Pair<String, String> = "line 1" to "line 2",
    var legacyEntry: Pair<String, String> = "line 1" to "line 2",
    var cooldown: Long = 0L,
    var expiredCooldown: String = "Right now.",
    var shortCooldown: Boolean = false,
)