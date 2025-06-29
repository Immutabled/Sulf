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

class WhitelistAddCommand(
    private val plugin: SulfPlugin,
    private val module: WhitelistModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (args.isEmpty()) {
            source.sendMessage(Component.text("Usage: /whitelist add <player>", NamedTextColor.RED))
            return
        }

        val playerName = args[0]

        if (this.module.config.whitelistUsers.contains(playerName)) {
            source.sendMessage(Component.text("The player is already in the whitelist.", NamedTextColor.YELLOW))
            return
        }

         this.module.config.whitelistUsers.add(
            playerName
        )

        plugin.server.eventManager.fireAndForget(
            WhitelistConfigEvent(
                "whitelistUsers",
                this.module.config.whitelistUsers,
            )
        )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
                    .append(Component.text("Added ").color(NamedTextColor.GREEN))
                    .append(Component.text(playerName).color(NamedTextColor.WHITE))
                    .append(Component.text(" to the whitelist by ").color(NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source)).color(NamedTextColor.WHITE))
                    .append(Component.text(".").color(NamedTextColor.GREEN))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Whitelist added ").color(NamedTextColor.GREEN))
                .append(Component.text(playerName).color(NamedTextColor.WHITE))
                .append(Component.text(" to the whitelist.", NamedTextColor.GREEN))
                .build()
        )
    }
}