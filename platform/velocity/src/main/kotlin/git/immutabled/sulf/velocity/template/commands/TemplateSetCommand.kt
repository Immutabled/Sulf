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
 * @since abril 07, 10:07
 */
class TemplateSetCommand(
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
            source.sendMessage(Component.text("Usage: /template set <name>", NamedTextColor.RED))
            return
        }

        val templateName = args[0]
        val template = module.config.templates.find { it.name.equals(templateName, ignoreCase = true) }

        if (template == null) {
            source.sendMessage(Component.text("Template not found!", NamedTextColor.RED))
            return
        }

        plugin.server.eventManager.fireAndForget(
            TemplateConfigEvent(
                "set",
                value = templateName
            )
        )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Template '", NamedTextColor.GREEN))
                    .append(Component.text(templateName, NamedTextColor.WHITE))
                    .append(Component.text("' has been set ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Template '", NamedTextColor.GREEN))
                .append(Component.text(templateName, NamedTextColor.WHITE))
                .append(Component.text("' has set!", NamedTextColor.GREEN))
                .build()
        )
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String?> {
        val args = invocation.arguments()
        return if (args.size == 1) {
            module.config.templates.map { it.name }.filter { it.startsWith(args[0], ignoreCase = true) }.toMutableList()
        } else {
            mutableListOf()
        }
    }
}