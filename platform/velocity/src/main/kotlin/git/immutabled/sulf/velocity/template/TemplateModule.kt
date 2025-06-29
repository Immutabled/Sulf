package git.immutabled.sulf.velocity.template

import git.immutabled.moshi.ConfigUtil
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.PluginModule
import git.immutabled.sulf.velocity.template.commands.TemplateCommand
import git.immutabled.sulf.velocity.template.connector.TemplateConfigPacketListener
import git.immutabled.sulf.velocity.template.listener.TemplateConfigListener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:09
 */
class TemplateModule : PluginModule {

    lateinit var config: TemplateConfig

    override fun load(plugin: SulfPlugin) {
        this.config = ConfigUtil.register(
            "template",
            "Sulf",
            plugin.dataDirectory.toFile(),
            TemplateConfig()
        )

        plugin.server.commandManager.register("template", TemplateCommand(plugin, this))
        plugin.server.eventManager.register(plugin, TemplateConfigListener(plugin.connector))
        plugin.connector.addListener(TemplateConfigPacketListener)
    }

    override fun unload(plugin: SulfPlugin) {
        ConfigUtil.save(config)
    }
}