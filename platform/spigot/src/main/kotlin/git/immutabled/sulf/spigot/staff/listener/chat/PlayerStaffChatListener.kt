package git.immutabled.sulf.spigot.staff.listener.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.ClickEvent.clickEvent
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.api.events.staff.PlayerStaffChatEvent
import git.immutabled.sulf.api.events.staff.StaffAlertEvent
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_PERMISSION
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 15:59
 */
class PlayerStaffChatListener(
    private val plugin: Plugin
) : Listener {

    @EventHandler
    fun onStaffChat(event: PlayerStaffChatEvent) {

        if (!event.player.hasPermission(STAFF_PERMISSION)) {
            return
        }

        plugin.bridge!!.getPlayerServer(event.player).thenAccept { serverName ->

            val component = Component.text()
                .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
                .append(Component.text("[").color(NamedTextColor.GRAY))
                .append(Component.text((serverName.takeIf { it != "unknown" } ?: "local").capitalize()).color(NamedTextColor.GRAY))
                .apply {
                        clickEvent(ClickEvent.Action.RUN_COMMAND, "/server $serverName")
                }
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(plugin.coreHook.getDisplayName(event.player, false)).color(NamedTextColor.WHITE)
                .append(Component.text(": ").color(NamedTextColor.GRAY))
                .append(Component.text(event.message).color(NamedTextColor.LIGHT_PURPLE))
                .build()

            this.plugin.server.pluginManager.callEvent(StaffAlertEvent(component))
        }
    }
}