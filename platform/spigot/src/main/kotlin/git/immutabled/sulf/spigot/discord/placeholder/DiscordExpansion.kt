package git.immutabled.sulf.spigot.discord.placeholder

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.discord.DiscordModule
import git.immutabled.sulf.spigot.discord.runnable.DiscordRunnable

class DiscordExpansion(
    private val plugin: Plugin,
    private val module: DiscordModule
) : PlaceholderExpansion() {

    override fun getIdentifier(): String = plugin.name.lowercase()
    override fun getAuthor(): String = plugin.description.authors.joinToString()
    override fun getVersion(): String = plugin.description.version
    override fun persist(): Boolean = true

    override fun onPlaceholderRequest(player: org.bukkit.entity.Player?, params: String): String? {
        return when (params.lowercase()) {
            "discord_online" -> this.module.online.toString()
            "discord_total" -> this.module.members.toString()
            else -> null
        }
    }
}