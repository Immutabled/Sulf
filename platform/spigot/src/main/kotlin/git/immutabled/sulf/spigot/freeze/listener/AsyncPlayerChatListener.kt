package git.immutabled.sulf.spigot.freeze.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.ClickEvent.clickEvent
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.api.events.staff.StaffAlertEvent
import git.immutabled.sulf.spigot.FREEZE_METADATA_KEY
import git.immutabled.sulf.spigot.Plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 18:57
 */
class AsyncPlayerChatListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        if (event.player.hasMetadata(FREEZE_METADATA_KEY)) {
            plugin.server.scheduler.runTask(plugin, Runnable {
            plugin.bridge!!.getPlayerServer(event.player).thenAccept { serverName ->

                val component = Component.text()
                    .append(Component.text("[Freeze] ").color(NamedTextColor.GOLD))
                    .append(Component.text("[").color(NamedTextColor.GRAY))
                    .append(Component.text(serverName.takeIf { it != "unknown" } ?: "local").color(NamedTextColor.GRAY))
                    .apply {
                        clickEvent(ClickEvent.Action.RUN_COMMAND, "/server $serverName")
                    }
                    .append(Component.text("] ").color(NamedTextColor.GRAY))
                    .append(event.player.displayName()).color(NamedTextColor.WHITE)
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(event.message).color(NamedTextColor.YELLOW))

                    plugin.server.pluginManager.callEvent(StaffAlertEvent(component.build()))
                }
            })

            event.isCancelled = true
        }
    }
}