package git.immutabled.sulf.velocity.template.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.moshi.TimeUtil
import git.immutabled.sulf.velocity.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.template.event.TemplateConfigEvent
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:07
 */
class TemplateSetTimeCommand(
    private val plugin: SulfPlugin,
    private val module: TemplateModule
) : SimpleCommand {

    private val timePattern = Pattern.compile("^(\\d+)([smhd]?)$", Pattern.CASE_INSENSITIVE)


    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (!source.hasPermission(STAFF_PERMISSION)) {
            source.sendMessage(NO_PERMISSION_MESSAGE)
            return
        }

        if (args.size < 2) {
            source.sendMessage(Component.text("Usage: /template settime <name> <seconds>", NamedTextColor.RED))
            return
        }

        val templateName = args[0]
        val template = module.config.templates.find { it.name.equals(templateName, ignoreCase = true) }

        if (template == null) {
            source.sendMessage(Component.text("Template not found!", NamedTextColor.RED))
            return
        }
        val timeInput = args[1]
        val amount = TimeUtil.parseTime(timeInput)

        if (amount <= 0) {
            source.sendMessage(Component.text("Time amount must be positive", NamedTextColor.RED))
            return
        }

        plugin.server.eventManager.fireAndForget(
            TemplateConfigEvent(
                "time",
                templateName,
                amount
            )
        )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Display time for template '", NamedTextColor.GREEN))
                    .append(Component.text(templateName, NamedTextColor.WHITE))
                    .append(Component.text("' set to ", NamedTextColor.GREEN))
                    .append(Component.text("$timeInput seconds", NamedTextColor.WHITE))
                    .append(Component.text(" by ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Display time for template '", NamedTextColor.GREEN))
                .append(Component.text(templateName, NamedTextColor.WHITE))
                .append(Component.text("' set to ", NamedTextColor.GREEN))
                .append(Component.text("$timeInput seconds", NamedTextColor.WHITE))
                .build()
        )
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String> {
        val args = invocation.arguments()
        return if (args.size == 1) {
            module.config.templates.map { it.name }.filter { it.startsWith(args[0], ignoreCase = true) }.toMutableList()
        } else {
            mutableListOf()
        }
    }
}