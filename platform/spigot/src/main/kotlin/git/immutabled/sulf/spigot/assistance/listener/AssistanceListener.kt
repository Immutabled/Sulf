package git.immutabled.sulf.spigot.assistance.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.spigot.assistance.VelocityBridge
import git.immutabled.sulf.api.events.assistance.AssistanceEvent
import git.immutabled.sulf.api.events.staff.StaffAlertEvent
import git.immutabled.sulf.spigot.ASSISTANCE_PID
import git.immutabled.sulf.spigot.Plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 10:45
 */
class AssistanceListener(private val bridge: VelocityBridge,
                         private val plugin: Plugin) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onAssistanceEvent(event: AssistanceEvent) {
        bridge.getPlayerServer(event.player).thenAccept { serverName ->
            val message = Component.text()
                .append(Component.text("[").color(NamedTextColor.BLUE))
                .append(Component.text(if(event.isHelpOp) "Request" else "Report").color(NamedTextColor.BLUE))
                .append(Component.text("]").color(NamedTextColor.BLUE))
                .append(Component.text(" [").color(NamedTextColor.GRAY))
                .append(Component.text((serverName.takeIf { it != "unknown" } ?: "local").capitalize()).color(NamedTextColor.GRAY))
//                .apply {
//                    if (serverName != "unknown" && serverName != bridge.getPlayerServer(event.player).join()) {
//                        clickEvent(ClickEvent.Action.RUN_COMMAND, "/server $serverName")
//                    }
//                }
                .append(Component.text("]").color(NamedTextColor.GRAY))
                .append(Component.space())

                .append(if (event.isHelpOp) buildHelpOpContent(event) else buildReportContent(event))

                .append(Component.newline())
                .append(Component.text("     ").append(Component.text("Reason: ").color(NamedTextColor.BLUE))
                    .append(Component.text(event.reason).color(NamedTextColor.GRAY)))

            Bukkit.getServer().pluginManager.callEvent(StaffAlertEvent(message.build()))
        }

        val feedback = if (event.isHelpOp) {
            Component.text("Your help request has been sent to the staff team.", NamedTextColor.GREEN)
        } else {
            Component.text("Your report has been sent to the staff team.", NamedTextColor.GREEN)
        }
        event.player.sendMessage(feedback)

        this.plugin.getConnector().sendPacket(
            ASSISTANCE_PID, mapOf(
            "id" to event.player.uniqueId.toString()
        ))
    }

    private fun buildHelpOpContent(event: AssistanceEvent): Component {
        return Component.text()
            .append(buildPlayerComponent(event.player))
            .append(Component.space())
            .append(Component.text("requested assistance").color(NamedTextColor.GRAY))
            .build()
    }

    private fun buildReportContent(event: AssistanceEvent): Component {
        return event.reported?.let { reported ->
            Component.text()
                .append(buildPlayerComponent(null,reported.name))
                .append(Component.space())
                .append(Component.text("(${getReportsCount(reported)}) ").color(NamedTextColor.GRAY))
                .append(Component.text("reported by ").color(NamedTextColor.GRAY))
                .append(buildPlayerComponent(event.player))
                .build()
        } ?: Component.empty()
    }

    private fun buildPlayerComponent(player:Player?, username: String = player!!.name): Component {
        return if (player != null) { this.plugin.coreHook.getDisplayName(player, true)} else { Component.text(username) }
            .hoverEvent(HoverEvent.showText(
                Component.text("Click to teleport to $username", NamedTextColor.GREEN)
            ))
            .clickEvent(ClickEvent.runCommand("/teleport $username"))
    }

    private fun getReportsCount(player: Player): Int {
        return 0
    }
}