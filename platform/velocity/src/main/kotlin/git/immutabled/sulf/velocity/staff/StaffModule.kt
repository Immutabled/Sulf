package git.immutabled.sulf.velocity.staff

import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.PluginModule
import git.immutabled.sulf.velocity.staff.connector.StaffAlertPacketListener
import git.immutabled.sulf.velocity.staff.listener.DisconnectListener
import git.immutabled.sulf.velocity.staff.listener.ServerConnectedListener
import git.immutabled.sulf.velocity.staff.listener.StaffAlertListener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:28
 */
class StaffModule : PluginModule {

    override fun load(plugin: SulfPlugin) {
        plugin.server.eventManager.register(plugin,
            git.immutabled.sulf.velocity.staff.listener.DisconnectListener(plugin)
        )
        plugin.server.eventManager.register(plugin, ServerConnectedListener(plugin))
        plugin.server.eventManager.register(plugin, StaffAlertListener(plugin.connector))

        plugin.connector.addListener(StaffAlertPacketListener)
    }

    override fun unload(plugin: SulfPlugin) {

    }
}