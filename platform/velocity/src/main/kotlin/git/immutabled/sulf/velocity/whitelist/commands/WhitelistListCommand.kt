package git.immutabled.sulf.velocity.whitelist.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.whitelist.WhitelistModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:13
 */
class WhitelistListCommand(
    private val module: WhitelistModule
) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()

        val whitelist = module.config.whitelistUsers

        if (whitelist.isEmpty()) {
            source.sendMessage(Component.text("The whitelist is currently empty.", NamedTextColor.YELLOW))
            return
        }

        val message = Component.text()
            .append(Component.text("Whitelisted players (${whitelist.size}):\n", NamedTextColor.GOLD))
            .append(Component.text("\n", NamedTextColor.GRAY))
            .append(Component.text(whitelist.joinToString(", "), NamedTextColor.WHITE))
            .build()

        source.sendMessage(message)
    }
}