package git.immutabled.sulf.spigot.support

import git.immutabled.moshi.ConfigUtil
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.support.commands.SupportCommand
import git.immutabled.sulf.spigot.support.listener.PlayerJoinListener
import git.immutabled.sulf.spigot.support.listener.SupportListener
import org.bukkit.command.CommandExecutor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 13, 23:48
 */
class SupportModule : PluginModule {

    lateinit var config: SupportConfig

    override fun load(plugin: Plugin) {
        this.config = ConfigUtil.register("support", plugin.name, plugin.dataFolder, SupportConfig())
        plugin.server.pluginManager.registerEvents(PlayerJoinListener(this), plugin)
        plugin.server.pluginManager.registerEvents(SupportListener(this), plugin)

        plugin.registerCommand("support", SupportCommand(this), config.supports)
    }

    override fun unload(plugin: Plugin) {
        ConfigUtil.save(this.config)
    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return emptyMap()
    }
}