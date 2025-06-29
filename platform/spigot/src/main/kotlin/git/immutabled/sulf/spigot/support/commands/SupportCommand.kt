package git.immutabled.sulf.spigot.support.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import git.immutabled.sulf.spigot.NO_CONSOLE_MESSAGE
import git.immutabled.sulf.spigot.support.SupportModule
import git.immutabled.sulf.spigot.support.event.SupportEvent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 13, 23:53
 */
class SupportCommand(private val module: SupportModule) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(NO_CONSOLE_MESSAGE)
            return true
        }

        if (!command.name.equals("support", true)) {
            if (!module.config.enabled) {
                sender.sendMessage(Component.text("El sistema de partners está desactivado", NamedTextColor.RED))
                return true
            }

            if (module.config.claims.contains(sender.uniqueId)) {
                sender.sendMessage(Component.text("Ya has reclamado tu recompensa de partner", NamedTextColor.RED))
                return true
            }

            if (module.config.supports.none { it.equals(command.name, ignoreCase = true) }) {
                sender.sendMessage(Component.text("'${command.name}' no está registrado como un partner", NamedTextColor.RED))
                return true
            }

            Bukkit.getServer().pluginManager.callEvent(SupportEvent(sender, command.name))
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(Component.text("Uso: /support <partner|toggle|add|remove|reset|list>", NamedTextColor.RED))
            return true
        }

        when (val subCommand = args[0].lowercase()) {
            "toggle" -> {
                module.config.enabled = !module.config.enabled
                sender.sendMessage(
                    Component.text(
                        "El sistema de partners ha sido ${if (module.config.enabled) "activado" else "desactivado"}",
                        NamedTextColor.GREEN
                    )
                )
                return true
            }

            "add" -> {
                if (args.size < 2) {
                    sender.sendMessage(Component.text("Uso: /support add <partner>", NamedTextColor.RED))
                    return true
                }

                if (module.config.supports.contains(args[1])) {
                    sender.sendMessage(Component.text("Ya existe un partner con ese nombre", NamedTextColor.RED))
                    return true
                }

                module.config.supports.add(args[1])
                sender.sendMessage(Component.text("Partner ${args[1]} agregado", NamedTextColor.GREEN))
                return true
            }

            "remove" -> {
                if (args.size < 2) {
                    sender.sendMessage(Component.text("Uso: /support remove <partner>", NamedTextColor.RED))
                    return true
                }

                if (!module.config.supports.contains(args[1])) {
                    sender.sendMessage(Component.text("No existe un partner con ese nombre", NamedTextColor.RED))
                    return true
                }

                module.config.supports.remove(args[1])
                sender.sendMessage(Component.text("Partner ${args[1]} eliminado", NamedTextColor.GREEN))
                return true
            }

            "reset" -> {
                if (args.size < 2) {
                    sender.sendMessage(Component.text("Uso: /support reset <player|all>", NamedTextColor.RED))
                    return true
                }

                if (args[1].equals("all", true)) {
                    module.config.claims.clear()
                    sender.sendMessage(
                        Component.text(
                            "Todos los usuarios que han reclamado sus recompensas han sido eliminados",
                            NamedTextColor.GREEN
                        )
                    )
                    return true
                }

                val player = Bukkit.getPlayer(args[1])
                if (player == null) {
                    sender.sendMessage(Component.text("Jugador no encontrado", NamedTextColor.RED))
                    return true
                }

                module.config.claims.remove(player.uniqueId)
                sender.sendMessage(
                    Component.text(
                        "Reclamaciones de ${player.name} han sido eliminadas",
                        NamedTextColor.GREEN
                    )
                )
                return true
            }

            "list" -> {
                if (module.config.supports.isEmpty()) {
                    sender.sendMessage(Component.text("No hay partners registrados", NamedTextColor.RED))
                    return true
                }

                val message = Component.text()
                    .append(Component.text("¡Lista de partners!\n", NamedTextColor.GOLD, TextDecoration.BOLD))
                    .append(Component.text("Partners actuales (${module.config.supports.size}):\n", NamedTextColor.YELLOW))

                module.config.supports.forEachIndexed { index, partner ->
                    message.append(Component.text("${index + 1}. $partner\n", NamedTextColor.WHITE))
                }

                sender.sendMessage(message)
                return true
            }

            else -> {
                if (!module.config.enabled) {
                    sender.sendMessage(Component.text("El sistema de partners está desactivado", NamedTextColor.RED))
                    return true
                }

                if (module.config.claims.contains(sender.uniqueId)) {
                    sender.sendMessage(Component.text("Ya has reclamado tu recompensa de partner", NamedTextColor.RED))
                    return true
                }

                if (module.config.supports.none { it.equals(subCommand, ignoreCase = true) }) {
                    sender.sendMessage(Component.text("'$subCommand' no está registrado como un partner", NamedTextColor.RED))
                    return true
                }

                Bukkit.getServer().pluginManager.callEvent(SupportEvent(sender, subCommand))
                return true
            }
        }
    }
}