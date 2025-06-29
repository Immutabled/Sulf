package git.immutabled.sulf.velocity

import com.velocitypowered.api.event.ResultedEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import git.immutabled.sulf.velocity.whitelist.WhitelistModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:04
 */
const val WHITELIST_CONFIG_PID = "whitelist_config"
const val TEMPLATE_CONFIG_PID = "template_config"
const val STAFF_ALERT_PID = "staff_alert"
const val PROXY_UPDATE_PID = "proxy_update"

val NO_PERMISSION_MESSAGE = Component.text("No permission.", NamedTextColor.RED)
val NO_CONSOLE_MESSAGE = Component.text("This command can only be executed in-game.", NamedTextColor.RED)
val NO_HUB_SUITABLE = Component.text( "There is no hub suitable for you.", NamedTextColor.RED)

const val TEMPLATE_TIME_PLACEHOLDER = "%time%"

const val STAFF_PERMISSION = "sulf.staff"

fun kickComponent(module: WhitelistModule): ResultedEvent.ComponentResult {
    val rankName = SulfPlugin.get().hook.getGroupDisplayName(module.config.whitelistRank)

    val fullMessage = module.config.whitelistKickMessage
        .map { line ->
            val replaced = line.replace("%rank%", rankName)
            LegacyComponentSerializer.legacyAmpersand().deserialize(replaced)
        }
        .fold(Component.empty()) { acc, component ->
            acc.append(component).append(Component.newline())
        }

    return ResultedEvent.ComponentResult.denied(fullMessage)

}
