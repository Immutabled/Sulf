package git.immutabled.sulf.velocity.whitelist.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.velocity.NO_PERMISSION_MESSAGE
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.STAFF_PERMISSION
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.whitelist.WhitelistModule
import git.immutabled.sulf.velocity.whitelist.event.WhitelistConfigEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:18
 */
class WhitelistSetFallbackTemplateCommand(
    private val plugin: SulfPlugin,
    private val module: WhitelistModule,
    private val template: TemplateModule
) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (!source.hasPermission(STAFF_PERMISSION)) {
            source.sendMessage(NO_PERMISSION_MESSAGE)
            return
        }

        if (args.isEmpty()) {
            source.sendMessage(Component.text("Current template: ${module.config.postTemplate ?: "none"}", NamedTextColor.YELLOW))
            source.sendMessage(Component.text("Usage: /whitelist fallback <template>", NamedTextColor.RED))
            return
        }

        val template = args.joinToString(" ")
        module.config.postTemplate = template

        plugin.server.eventManager.fireAndForget(
            WhitelistConfigEvent(
                "fallback",
                template
            )
        )

        plugin.server.eventManager.fireAndForget(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ", NamedTextColor.BLUE))
                    .append(Component.text("Whitelist fallback set to ", NamedTextColor.GREEN))
                    .append(Component.text(template, NamedTextColor.YELLOW))
                    .append(Component.text(" by ", NamedTextColor.GREEN))
                    .append(Component.text(plugin.hook.getDisplayName(source), NamedTextColor.WHITE))
                    .append(Component.text(".", NamedTextColor.GREEN))
                    .build()
            )
        )

        source.sendMessage(
            Component.text()
                .append(Component.text("Whitelist fallback template set to: ", NamedTextColor.GREEN))
                .append(Component.text(template, NamedTextColor.WHITE))
                .build()
        )
    }

    override fun suggest(invocation: SimpleCommand.Invocation): MutableList<String?> {
        val args = invocation.arguments()
        return if (args.size == 1) {
            this.template.config.templates.map { it.name }.filter { it.startsWith(args[0], ignoreCase = true) }.toMutableList()
        } else {
            mutableListOf()
        }
    }
}