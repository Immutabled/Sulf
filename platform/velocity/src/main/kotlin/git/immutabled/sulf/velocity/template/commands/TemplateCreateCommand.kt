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
 * @since abril 07, 10:06
 */
class TemplateCreateCommand(
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

        if (args.isEmpty()) {
            source.sendMessage(Component.text("Usage: /template create <name>", NamedTextColor.RED))
            return
        }

        val templateName = args[0]

        if (module.config.templates.any { it.name.equals(templateName, ignoreCase = true) }) {
            source.sendMessage(Component.text("A template with that name already exists!", NamedTextColor.RED))
            return
        }

        plugin.server.eventManager.fireAndForget(
            TemplateConfigEvent(
                "create",
                value = templateName
            )
        )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Template '", NamedTextColor.GREEN))
                    .append(Component.text(templateName, NamedTextColor.WHITE))
                    .append(Component.text("' created by ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Template '", NamedTextColor.GREEN))
                .append(Component.text(templateName, NamedTextColor.WHITE))
                .append(Component.text("' created successfully!", NamedTextColor.GREEN))
                .build()
        )
    }

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        return emptyList()
    }
}