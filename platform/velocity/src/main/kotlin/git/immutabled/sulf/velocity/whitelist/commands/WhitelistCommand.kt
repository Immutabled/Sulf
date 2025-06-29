package git.immutabled.sulf.velocity.whitelist.commands

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import git.immutabled.sulf.velocity.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.whitelist.WhitelistModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:46
 */
class WhitelistCommand(
    plugin: SulfPlugin,
    module: WhitelistModule,
    template: TemplateModule
) : SimpleCommand {

    private val subCommands: Map<String, SimpleCommand> = mapOf(
        "add" to WhitelistAddCommand(plugin, module),
        "remove" to WhitelistRemoveCommand(plugin, module),
        "list" to WhitelistListCommand(module),
        "rank" to WhitelistRankCommand(plugin, module),
        "on" to WhitelistOnCommand(plugin, module),
        "off" to WhitelistOffCommand(plugin, module),
        "type" to WhitelistTypeCommand(plugin, module),
        "template" to WhitelistSetTemplateCommand(plugin, module,template),
        "fallback" to WhitelistSetFallbackTemplateCommand(plugin, module,template)
    )

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (args.isEmpty()) {
            sendHelp(source)
            return
        }

        val subCommand = args[0].lowercase()
        val subCommandExecutor = subCommands[subCommand]

        if (subCommandExecutor == null) {
            sendHelp(source)
            return
        }

        if (!source.hasPermission(STAFF_PERMISSION)) {
            source.sendMessage(NO_PERMISSION_MESSAGE)
            return
        }

        val subCommandInvocation = object : SimpleCommand.Invocation {
            override fun source(): CommandSource = source
            override fun alias(): String = subCommand
            override fun arguments(): Array<String> = args.drop(1).toTypedArray()
        }

        subCommandExecutor.execute(subCommandInvocation)
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String> {
        val args = invocation.arguments()

        return when {
            args.isEmpty() || args.size == 1 -> {
                subCommands.keys.filter { it.startsWith(args.getOrNull(0) ?: "") }
                    .toMutableList()
            }
            else -> {
                val subCommand = args[0].lowercase()
                subCommands[subCommand]?.let { cmd ->
                    val suggestionInvocation = object : SimpleCommand.Invocation {
                        override fun source(): CommandSource = invocation.source()
                        override fun alias(): String = subCommand
                        override fun arguments(): Array<String> = args.drop(1).toTypedArray()
                    }
                    cmd.suggest(suggestionInvocation).toMutableList()
                } ?: mutableListOf()
            }
        }
    }

    private fun sendHelp(source: CommandSource) {
        source.sendMessage(
            Component.text()
                .content("-----------------------------------")
                .color(NamedTextColor.BLUE)
                .decoration(TextDecoration.STRIKETHROUGH, true)
                .build()
        )

        subCommands.forEach { (name, cmd) ->
            source.sendMessage(
                Component.text()
                    .append(Component.text("/whitelist $name", NamedTextColor.RED))
                    .append(Component.space())
                    .append(getCommandUsage(cmd))
                    .append(Component.text(" - ", NamedTextColor.GRAY))
                    .append(getCommandDescription(cmd))
                    .build()
            )
        }

        source.sendMessage(
            Component.text()
                .content("-----------------------------------")
                .color(NamedTextColor.BLUE)
                .decoration(TextDecoration.STRIKETHROUGH, true)
                .build()
        )
    }

    private fun getCommandUsage(cmd: SimpleCommand): Component {
        return when (cmd) {
            is WhitelistAddCommand -> Component.text("<player>", NamedTextColor.WHITE)
            is WhitelistRemoveCommand -> Component.text("<player>", NamedTextColor.WHITE)
            is WhitelistSetTemplateCommand -> Component.text("<template>", NamedTextColor.WHITE)
            is WhitelistSetFallbackTemplateCommand -> Component.text("<template>", NamedTextColor.WHITE)
            is WhitelistRankCommand -> Component.text("[rank]", NamedTextColor.WHITE)
            else -> Component.empty()
        }
    }

    private fun getCommandDescription(cmd: SimpleCommand): Component {
        return when (cmd) {
            is WhitelistAddCommand -> Component.text("Add player to whitelist", NamedTextColor.GRAY)
            is WhitelistRemoveCommand -> Component.text("Remove player from whitelist", NamedTextColor.GRAY)
            is WhitelistListCommand -> Component.text("List whitelisted players", NamedTextColor.GRAY)
            is WhitelistSetTemplateCommand -> Component.text("Set whitelist template", NamedTextColor.GRAY)
            is WhitelistRankCommand -> Component.text("Set required rank", NamedTextColor.GRAY)
            is WhitelistOnCommand -> Component.text("Enable whitelist", NamedTextColor.GRAY)
            is WhitelistOffCommand -> Component.text("Disable whitelist", NamedTextColor.GRAY)
            is WhitelistSetFallbackTemplateCommand -> Component.text("Set fallback template", NamedTextColor.GRAY)
            is WhitelistTypeCommand -> Component.text("Set whitelist type", NamedTextColor.GRAY)
            else -> Component.empty()
        }
    }
}