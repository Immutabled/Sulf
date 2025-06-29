package git.immutabled.sulf.spigot

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.command.CommandExecutor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:13
 */
interface PluginModule {

    fun load(plugin: Plugin)
    fun unload(plugin: Plugin)
    fun commands(): Map<List<String>, CommandExecutor>

    fun expansions(plugin: Plugin) : Set<PlaceholderExpansion> = setOf()
}