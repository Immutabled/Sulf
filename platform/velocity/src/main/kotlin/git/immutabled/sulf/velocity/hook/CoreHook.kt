package git.immutabled.sulf.velocity.hook

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import git.immutabled.sulf.velocity.SulfPlugin

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 11:58
 */
interface CoreHook {

    fun load(plugin: SulfPlugin)

    fun getDisplayName(player: Player): String

    fun getDisplayName(source: CommandSource): String {
        return when (source) {
            is Player -> getDisplayName(source)
            else -> "Console"
        }
    }

    fun getGroupDisplayName(group: String): String

    fun getGroupDisplayName(player: Player): String

    fun getRanks(): List<String>
}