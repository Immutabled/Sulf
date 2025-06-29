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
 * @since abril 07, 13:34
 */
class WhitelistOnCommand(
    private val plugin: SulfPlugin,
    private val module: WhitelistModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()

        if (module.config.whitelisted) {
            source.sendMessage(Component.text("The whitelist is already enabled.", NamedTextColor.RED))
            return
        }

        plugin.server.eventManager.fireAndForget(
            WhitelistConfigEvent(
                "whitelisted",
                true
            )
        )
        plugin.logger.info("Whitelist Config Data sent")

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Whitelist has been ", NamedTextColor.GREEN))
                    .append(Component.text("enabled", NamedTextColor.YELLOW))
                    .append(Component.text(" by ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Whitelist has been ", NamedTextColor.GREEN))
                .append(Component.text("disabled", NamedTextColor.RED))
                .build()
        )
    }
}