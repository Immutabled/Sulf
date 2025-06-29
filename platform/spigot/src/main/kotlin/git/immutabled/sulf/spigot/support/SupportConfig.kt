package git.immutabled.sulf.spigot.support

import git.immutabled.moshi.JsonConfig
import java.util.*

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 13, 23:55
 */
class SupportConfig: JsonConfig() {

    var enabled: Boolean = false

    var supports: MutableList<String> = mutableListOf()

    var commands: List<String> = listOf()

    var messages: List<String> = listOf(
     "§7§lSupport",
     ""
    )

    var claims: MutableList<UUID> = mutableListOf()

}