package git.immutabled.sulf.velocity.whitelist.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.whitelist.Type
import git.immutabled.sulf.velocity.whitelist.WhitelistModule
import git.immutabled.sulf.velocity.whitelist.event.WhitelistConfigEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 13:34
 */
class WhitelistTypeCommand(
    private val plugin: SulfPlugin,
    private val module: WhitelistModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()

        plugin.server.eventManager.fireAndForget(
            WhitelistConfigEvent(
                "type",
                if (module.config.type == Type.PLAYER) {
                    Type.RANK.name
                } else {
                    Type.PLAYER.name
                }
            )
        )
        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Whitelist has been ", NamedTextColor.GREEN))
                    .append(Component.text("post", NamedTextColor.YELLOW))
                    .append(Component.text(" in ", NamedTextColor.GREEN))
                    .append(Component.text(module.config.type.name.lowercase(), NamedTextColor.YELLOW))
                    .append(Component.text(" by ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Whitelist has is now in ", NamedTextColor.GREEN))
                .append(Component.text(module.config.type.name, NamedTextColor.YELLOW))
                .build()
        )
    }
}