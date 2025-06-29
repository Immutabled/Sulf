package git.immutabled.sulf.velocity.template.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.template.event.TemplateConfigEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:08
 */
class TemplateSetLineCommand(
    private val plugin: SulfPlugin,
    private val module: TemplateModule
) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (!source.hasPermission(STAFF_PERMISSION)) {
            source.sendMessage(NO_PERMISSION_MESSAGE)
            return
        }

        if (args.size < 2) {
            source.sendMessage(Component.text("Usage: /template setline <name> <legacy/normal> <firts/second> <content>", NamedTextColor.RED))
            return
        }

        val templateName = args[0]
        val templateType = args[1]
        val lineType = args[2]
        val content = args.drop(3).joinToString(" ")
        val template = module.config.templates.find { it.name.equals(templateName, ignoreCase = true) }

        if (template == null) {
            source.sendMessage(Component.text("Template not found!", NamedTextColor.RED))
            return
        }

        if (templateType != "legacy" && templateType != "normal") {
            source.sendMessage(Component.text("Invalid template type!", NamedTextColor.RED))
            return
        }

        if (lineType != "first" && lineType != "second") {
            source.sendMessage(Component.text("Invalid line type!", NamedTextColor.RED))
            return
        }

        plugin.server.eventManager.fireAndForget(
            TemplateConfigEvent(
                "${templateType}_$lineType",
                templateName,
                content
            )
        )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Line $lineType in template '", NamedTextColor.GREEN))
                    .append(Component.text(templateName, NamedTextColor.WHITE))
                    .append(Component.text("' updated by ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Line $lineType in template '", NamedTextColor.GREEN))
                .append(Component.text(templateName, NamedTextColor.WHITE))
                .append(Component.text("' updated!", NamedTextColor.GREEN))
                .build()
        )
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String?> {
        val args = invocation.arguments()
        return when {
            args.isEmpty() || args.size == 1 -> {
                module.config.templates.map { it.name }.filter { it.startsWith(args.getOrNull(0) ?: "", ignoreCase = true) }.toMutableList()
            }
            args.size == 2 -> {
                mutableListOf("legacy", "normal")
            }
            args.size == 3 -> {
                mutableListOf("first", "second")
            }
            else -> mutableListOf()
        }
    }
}