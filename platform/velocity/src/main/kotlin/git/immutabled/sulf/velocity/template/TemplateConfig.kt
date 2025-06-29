package git.immutabled.sulf.velocity.template

import git.immutabled.moshi.JsonConfig

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 09:57
 */
open class TemplateConfig : JsonConfig() {

    var currentTemplate: String = "default"

    var expiredTime: Long = 0L

    var templates = mutableListOf(
        Template(
            name = "default",
            entry = "line 1" to "line 2",
            cooldown = 0L,
        ),
        Template(
            name = "whitelisted",
            entry = "whitelisted line 1" to "whitelisted line 2",
            cooldown = 0L,
    )
)

    var hover: List<String> = mutableListOf(
        "This is a hover",
        "This is another hover"
    )



}