package git.immutabled.sulf.velocity.template.commands

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import git.immutabled.sulf.velocity.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.template.TemplateModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 11:38
 */
class TemplateCommand(
    plugin: SulfPlugin,
    module: TemplateModule
) : SimpleCommand {

    private val subCommands: Map<String, SimpleCommand> = mapOf(
        "create" to TemplateCreateCommand(plugin, module),
        "delete" to TemplateDeleteCommand(plugin, module),
        "preview" to TemplatePreviewCommand(module),
        "set" to TemplateSetCommand(plugin, module),
        "setline" to TemplateSetLineCommand(plugin, module),
        "settime" to TemplateSetTimeCommand(plugin, module)
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
                    .append(Component.text("/template $name", NamedTextColor.RED))
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
            is TemplateCreateCommand -> Component.text("<name>", NamedTextColor.WHITE)
            is TemplateDeleteCommand -> Component.text("<name>", NamedTextColor.WHITE)
            is TemplatePreviewCommand -> Component.text("<name> <legacy/normal>", NamedTextColor.WHITE)
            is TemplateSetCommand -> Component.text("<name>", NamedTextColor.WHITE)
            is TemplateSetLineCommand -> Component.text("<name> <legacy/normal> <firts/second> <content>", NamedTextColor.WHITE)
            is TemplateSetTimeCommand -> Component.text("<name> <time>", NamedTextColor.WHITE)
            else -> Component.empty()
        }
    }

    private fun getCommandDescription(cmd: SimpleCommand): Component {
        return when (cmd) {
            is TemplateCreateCommand -> Component.text("Create new template", NamedTextColor.GRAY)
            is TemplateDeleteCommand -> Component.text("Delete a template", NamedTextColor.GRAY)
            is TemplatePreviewCommand -> Component.text("Preview template content", NamedTextColor.GRAY)
            is TemplateSetCommand -> Component.text("Set template content", NamedTextColor.GRAY)
            is TemplateSetLineCommand -> Component.text("Set specific template line", NamedTextColor.GRAY)
            is TemplateSetTimeCommand -> Component.text("Set template display time", NamedTextColor.GRAY)
            else -> Component.empty()
        }
    }
}