package git.immutabled.sulf.velocity.whitelist.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.whitelist.WhitelistModule
import git.immutabled.sulf.velocity.whitelist.event.WhitelistConfigEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:11
 */
class WhitelistRemoveCommand(
    private val plugin: SulfPlugin,
    private val module: WhitelistModule
) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (args.isEmpty()) {
            source.sendMessage(Component.text("Usage: /whitelist remove <player>", NamedTextColor.RED))
            return
        }

        val playerName = args[0]

        if (module.config.whitelistUsers.remove(playerName)) {
            plugin.server.eventManager.fireAndForget(
                WhitelistConfigEvent(
                    "whitelistUsers",
                    module.config.whitelistUsers
                )
            )

            plugin.server.eventManager.fireAndForget(
                StaffAlertEvent(
                    Component.text()
                        .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
                        .append(Component.text("Removed ").color(NamedTextColor.RED))
                        .append(Component.text(playerName).color(NamedTextColor.WHITE))
                        .append(Component.text(" from the whitelist ").color(NamedTextColor.GREEN))
                        .append(Component.text("by ").color(NamedTextColor.GREEN))
                        .append(Component.text(plugin.hook.getDisplayName(source)).color(NamedTextColor.WHITE))
                        .append(Component.text(".").color(NamedTextColor.GREEN))
                        .build()
                )
            )
        } else {
            source.sendMessage(Component.text("The player was not in the whitelist.", NamedTextColor.YELLOW))
        }
    }
}