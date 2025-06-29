package git.immutabled.sulf.spigot.cross

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.cross.connector.CrossPacketListener
import git.immutabled.sulf.spigot.cross.placeholder.PlayersExpansion
import org.bukkit.command.CommandExecutor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 22, 20:07
 */
class CrossModule : PluginModule {
    override fun load(plugin: Plugin) {
        plugin.getConnector().addListener(CrossPacketListener(plugin))
    }

    override fun unload(plugin: Plugin) {
    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return emptyMap()
    }

    override fun expansions(plugin: Plugin): Set<PlaceholderExpansion> {
        return setOf(
            PlayersExpansion(
                plugin
        ))
    }
}