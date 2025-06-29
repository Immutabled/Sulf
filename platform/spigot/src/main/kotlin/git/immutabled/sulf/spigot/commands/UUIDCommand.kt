package git.immutabled.sulf.spigot.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class UUIDCommand(private val plugin: JavaPlugin) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("§cUso correcto: /$label <jugador>")
            return true
        }

        val targetName = args[0]
        val target: OfflinePlayer = Bukkit.getOfflinePlayer(targetName)

        if (!target.hasPlayedBefore() && !target.isOnline) {
            sender.sendMessage("§cNo se pudo encontrar el jugador \"$targetName\".")
            return true
        }

        val uuid = target.uniqueId.toString()

        val message = Component.text()
            .append(Component.text("El UUID de ", NamedTextColor.YELLOW))
            .append(Component.text(targetName, NamedTextColor.GOLD))
            .append(Component.text(" es: ", NamedTextColor.YELLOW))
            .append(
                Component.text(uuid, NamedTextColor.GREEN)
                    .clickEvent(ClickEvent.copyToClipboard(uuid))
                    .hoverEvent(HoverEvent.showText(Component.text("Click para copiar", NamedTextColor.GRAY)))
                    .decorate(TextDecoration.UNDERLINED)
            ).build()

        if (sender is Player) {
            sender.sendMessage(message)
        } else {
            sender.sendMessage("El UUID de $targetName es: $uuid")
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 1) {
            val partial = args[0].lowercase()
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter { it.lowercase().startsWith(partial) }
                .toMutableList()
        }
        return mutableListOf()
    }
}
