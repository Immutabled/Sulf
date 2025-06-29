package git.immutabled.sulf.spigot.freeze

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.freeze.command.FreezeCommand
import git.immutabled.sulf.spigot.freeze.connector.FreezePacketListener
import git.immutabled.sulf.spigot.freeze.listener.*
import org.bukkit.command.CommandExecutor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:13
 */
class FreezeModule(
    private val registry: FreezeRegistry
) : PluginModule {

    override fun load(plugin: Plugin) {
        plugin.server.pluginManager.registerEvents(EntityDamageByEntityListener(), plugin)
        plugin.server.pluginManager.registerEvents(BlockBreakListener(), plugin)
        plugin.server.pluginManager.registerEvents(BlockPlaceListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerMoveListener(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerFreezeListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(AsyncPlayerChatListener(plugin), plugin)
        plugin.server.pluginManager.registerEvents(PlayerJoinListener(plugin, this.registry), plugin)

        plugin.getConnector().addListener(FreezePacketListener(plugin, this.registry))
    }

    override fun unload(plugin: Plugin) {
    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return mapOf(
           listOf("freeze", "congelar", "ss", "unss", "unfreeze") to FreezeCommand(),
        )
    }
}