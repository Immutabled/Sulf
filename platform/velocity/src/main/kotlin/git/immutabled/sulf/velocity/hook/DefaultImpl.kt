package git.immutabled.sulf.velocity.hook

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import git.immutabled.sulf.velocity.SulfPlugin

class DefaultImpl(
    val plugin: SulfPlugin
) : CoreHook {

    override fun load(plugin: SulfPlugin) {
    }

    override fun getDisplayName(player: Player): String {
        return player.username
    }

    override fun getDisplayName(source: CommandSource): String {
        return when (source) {
            is Player -> getDisplayName(source)
            else -> "Console"
        }
    }

    override fun getGroupDisplayName(group: String): String {
        return group
    }

    override fun getGroupDisplayName(player: Player): String {
        return "none"
    }


    override fun getRanks(): List<String> {
        return listOf("none")
    }
}