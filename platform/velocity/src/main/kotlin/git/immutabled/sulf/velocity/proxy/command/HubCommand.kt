package git.immutabled.sulf.velocity.proxy.command

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import git.immutabled.sulf.velocity.NO_CONSOLE_MESSAGE
import git.immutabled.sulf.velocity.NO_HUB_SUITABLE
import git.immutabled.sulf.velocity.SulfPlugin

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:09
 */
class HubCommand(
    private val plugin: SulfPlugin
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (source !is Player) {
            source.sendMessage(NO_CONSOLE_MESSAGE)
            return
        }

        val hubs = getHubServers()

        when {
            hubs.isEmpty() -> {
                source.sendMessage(NO_HUB_SUITABLE)
                return
            }

            args.isEmpty() -> {
                val leastPopulatedHub = hubs.minByOrNull { it.playersConnected.size }
                leastPopulatedHub?.let { connectToHub(source, it) } ?:
                source.sendMessage(NO_HUB_SUITABLE)
            } else -> {
                val targetHub = hubs.find { it.serverInfo.name.equals(args[0], ignoreCase = true) }
                if (targetHub != null) {
                    connectToHub(source, targetHub)
                } else {
                    source.sendMessage(NO_HUB_SUITABLE)
                }
            }
        }
    }

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        return getHubServers().map { it.serverInfo.name }
    }

    private fun getHubServers(): List<RegisteredServer> {
        return this.plugin.server.allServers
            .filter { srv -> srv.serverInfo.name in plugin.config.suitables }
            .toList()
    }

    private fun connectToHub(player: Player, hub: RegisteredServer) {
        player.createConnectionRequest(hub).fireAndForget()
        player.sendMessage(Component.text("Sending you to ", NamedTextColor.GREEN)
            .append(Component.text(hub.serverInfo.name,NamedTextColor.GREEN, TextDecoration.BOLD))
            .append(Component.text(".", NamedTextColor.GREEN)))
    }
}