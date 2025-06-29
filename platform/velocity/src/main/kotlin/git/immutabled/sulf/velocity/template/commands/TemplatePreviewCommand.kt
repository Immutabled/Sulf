package git.immutabled.sulf.velocity.template.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.template.TemplateModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:07
 */
class TemplatePreviewCommand(
    private val module: TemplateModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (!source.hasPermission(STAFF_PERMISSION)) {
            source.sendMessage(NO_PERMISSION_MESSAGE)
            return
        }

        if (args.isEmpty()) {
            source.sendMessage(Component.text("Usage: /template preview <name> <legacy/normal>", NamedTextColor.RED))
            return
        }

        val templateName = args[0]
        val templateType = args[1]
        val template = module.config.templates.find { it.name.equals(templateName, ignoreCase = true) }

        if (template == null) {
            source.sendMessage(Component.text("Template not found!", NamedTextColor.RED))
            return
        }

        source.sendMessage(
            Component.text()
                .append(Component.text("--- Preview of '${template.name}' ---", NamedTextColor.GOLD))
                .build()
        )
        if (templateType != "legacy" && templateType != "normal") {
            source.sendMessage(Component.text("Invalid template type!", NamedTextColor.RED))
            return
        }

        val entry: Pair<String, String> = if (templateType == "legacy") {
            template.legacyEntry
        } else {
            template.entry
        }

        source.sendMessage(
            Component.text()
                .append(Component.text("> ", NamedTextColor.GRAY))
                .append(Component.text(entry.first, NamedTextColor.WHITE))
                .build()
        )
        source.sendMessage(
            Component.text()
                .append(Component.text("> ", NamedTextColor.GRAY))
                .append(Component.text(entry.second, NamedTextColor.WHITE))
                .build()
        )
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String?> {
        val args = invocation.arguments()
        return when {
            args.isEmpty() || args.size == 1 -> {
                module.config.templates.map { it.name }
                    .filter { it.startsWith(args.getOrNull(0) ?: "", ignoreCase = true) }.toMutableList()
            }
            args.size == 2 -> {
                mutableListOf("legacy", "normal")
            }
            else -> mutableListOf()
        }
    }
}