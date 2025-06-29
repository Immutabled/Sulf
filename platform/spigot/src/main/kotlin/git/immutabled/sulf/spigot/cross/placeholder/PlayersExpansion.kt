package git.immutabled.sulf.spigot.cross.placeholder

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import git.immutabled.sulf.spigot.Plugin

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 22, 19:15
 */
class PlayersExpansion(
    private val plugin: Plugin,
) : PlaceholderExpansion() {

    override fun getIdentifier(): String = "sulf"

    override fun getAuthor(): String = plugin.description.authors.joinToString()

    override fun getVersion(): String = plugin.description.version

    override fun persist(): Boolean = true

    override fun onPlaceholderRequest(player: org.bukkit.entity.Player?, params: String): String? {
        return when (params.lowercase()) {
            "velocity_online" -> this.plugin.players.toString()
            "velocity_total" -> this.plugin.slots.toString()
            else -> null
        }
    }
}