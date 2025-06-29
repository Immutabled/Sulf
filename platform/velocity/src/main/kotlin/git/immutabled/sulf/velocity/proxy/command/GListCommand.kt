package git.immutabled.sulf.velocity.proxy.command

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.server.ServerPing
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor.*
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.proxy.ProxyModule
import java.text.NumberFormat
import java.util.concurrent.CompletableFuture

class GListCommand(
    private val plugin: SulfPlugin,
    private val proxyInfoModule: ProxyModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val sender = invocation.source()

        val divider = line()
        sender.sendMessage(divider)

        for (server in plugin.server.allServers) {
            val pingFuture: CompletableFuture<ServerPing> = server.ping()

            try {
                val pingResult = pingFuture.get()

                if (pingResult != null) {
                    val onlinePlayers = server.playersConnected

                    if (onlinePlayers.isNotEmpty()) {
                        val serverId = server.serverInfo.name
                        var playersFormatted: Component = Component.empty()

                        onlinePlayers.forEachIndexed { index, player ->
                            playersFormatted = playersFormatted
                                .append(Component.text(plugin.hook.getDisplayName(player)))

                            if (index < onlinePlayers.size - 1) {
                                playersFormatted = playersFormatted.append(Component.text(", ", WHITE))
                            }

                            playersFormatted.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(
                                Component.text("Rank:", WHITE).append(Component.text(plugin.hook.getGroupDisplayName(player)))
                            ))
                        }

                        val message = Component.text("[$serverId] ", GOLD)
//                            .hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(
//                                Component.text("Click to go to server!", AQUA)
//                            ))

                            .append(Component.text("(${onlinePlayers.size}) ", WHITE)
                            )
                            .append(Component.text("[", WHITE))
                            .append(playersFormatted)
                            .append(Component.text("]", WHITE))

                        sender.sendMessage(message)
                    }
                }
            } catch (_: Exception) {}
        }

        val totalPlayers = proxyInfoModule.players().size
        val emptyLine = Component.empty()
        sender.sendMessage(emptyLine)

        val networkMessage = Component.text("Network Online: ", LIGHT_PURPLE)
            .append(Component.text(NumberFormat.getInstance().format(totalPlayers), WHITE))

        sender.sendMessage(networkMessage)
        sender.sendMessage(divider)
    }

    private fun line(): Component {
        return Component.text("------------------------------", GRAY)
    }
}
