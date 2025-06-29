package git.immutabled.sulf.spigot.assistance

import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.assistance.commands.HelpCommand
import git.immutabled.sulf.spigot.assistance.commands.ReportCommand
import git.immutabled.sulf.spigot.assistance.connector.AssistancePacketListener
import git.immutabled.sulf.spigot.assistance.listener.AssistanceCooldownListener
import git.immutabled.sulf.spigot.assistance.listener.AssistanceListener
import org.bukkit.command.CommandExecutor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 14:13
 */
class AssistanceModule(
    private val bridge: VelocityBridge,
    private val assistanceRegistry: AssistanceRegistry
) : PluginModule {

    override fun load(plugin: Plugin) {
        plugin.server.pluginManager.registerEvents(AssistanceListener(this.bridge, plugin), plugin)
        plugin.server.pluginManager.registerEvents(AssistanceCooldownListener(this.assistanceRegistry), plugin)

        plugin.getConnector().addListener(AssistancePacketListener(this.assistanceRegistry))
    }

    override fun unload(plugin: Plugin) {}

    override fun commands(): Map<List<String>, CommandExecutor> {
        return mapOf(
            listOf("ayuda", "helpop", "request") to HelpCommand(this.assistanceRegistry),
            listOf("report", "reporte", "reportar") to ReportCommand(this.assistanceRegistry)
        )
    }
}