package git.immutabled.sulf.velocity.whitelist

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.moshi.ConfigUtil
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.PluginModule
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.whitelist.commands.WhitelistCommand
import git.immutabled.sulf.velocity.whitelist.connector.WhitelistConfigPacketListener
import git.immutabled.sulf.velocity.whitelist.listener.LoginListener
import git.immutabled.sulf.velocity.whitelist.listener.WhitelistConfigListener
import git.immutabled.sulf.velocity.whitelist.listener.WhitelistFallbackListener
import java.util.concurrent.TimeUnit

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:10
 */
class WhitelistModule(
    private val template: TemplateModule
) : PluginModule {

    lateinit var config: WhitelistConfig

    override fun load(plugin: SulfPlugin) {
        this.config = ConfigUtil.register(
            "whitelist",
            "Sulf",
            plugin.dataDirectory.toFile(),
            WhitelistConfig()
        )

        plugin.server.commandManager.register("vwhitelist", WhitelistCommand(plugin, this,template))

        plugin.server.eventManager.register(plugin, WhitelistConfigListener(plugin.connector))
        plugin.server.eventManager.register(plugin, WhitelistFallbackListener(this, template))
        plugin.server.eventManager.register(plugin, LoginListener(plugin, this))

        plugin.connector.addListener(WhitelistConfigPacketListener)
    }

    override fun unload(plugin: SulfPlugin) {
        ConfigUtil.save(config)
    }
}