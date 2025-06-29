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
class WhitelistRankCommand(
    private val plugin: SulfPlugin,
    private val module: WhitelistModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        when {
            args.isEmpty() -> {
                source.sendMessage(Component.text("Usage: /whitelist rank <rank/clear>", NamedTextColor.RED))

                val currentRank = module.config.whitelistRank ?: "none"
                source.sendMessage(
                    Component.text()
                        .append(Component.text("Current whitelist rank: ", NamedTextColor.GOLD))
                        .append(Component.text(currentRank, NamedTextColor.WHITE))
                        .build()
                )
            }

            args[0].equals("clear", ignoreCase = true) -> {
                module.config.whitelistRank = "none"
                plugin.server.eventManager.fireAndForget(
                    WhitelistConfigEvent(
                        "whitelistRank",
                        "clear"
                    )
                )

                plugin.server.eventManager.fireAndForget(
                    StaffAlertEvent(
                        Component.text()
                            .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                            .append(Component.text("Whitelist rank requirement has been ", NamedTextColor.GREEN))
                            .append(Component.text("cleared", NamedTextColor.YELLOW))
                            .append(Component.text(" by ", NamedTextColor.GREEN))
                            .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                            .append(Component.text(".", NamedTextColor.GREEN))
                            .build()
                    )
                )

                source.sendMessage(
                    Component.text("Whitelist rank requirement has been removed.", NamedTextColor.GREEN)
                )
            }

            else -> {
                val newRank = args[0].lowercase()
                module.config.whitelistRank = newRank
                plugin.server.eventManager.fireAndForget(
                    WhitelistConfigEvent(
                        "whitelistRank",
                        newRank
                    )
                )

                plugin.server.eventManager.fireAndForget(
                    StaffAlertEvent(
                        Component.text()
                            .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                            .append(Component.text("Whitelist rank requirement set to ", NamedTextColor.GREEN))
                            .append(Component.text(newRank, NamedTextColor.YELLOW))
                            .append(Component.text(" by ", NamedTextColor.GREEN))
                            .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                            .append(Component.text(".", NamedTextColor.GREEN))
                            .build()
                    )
                )

                source.sendMessage(
                    Component.text()
                        .append(Component.text("Whitelist rank requirement set to: ", NamedTextColor.GREEN))
                        .append(Component.text(newRank, NamedTextColor.WHITE))
                        .build()
                )
            }
        }
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String> {
        val args = invocation.arguments()

        return when {
            args.isEmpty() || args.size == 1 -> {
                this.plugin.hook.getRanks().toMutableList()
            }
            else -> mutableListOf()
        }
    }
}