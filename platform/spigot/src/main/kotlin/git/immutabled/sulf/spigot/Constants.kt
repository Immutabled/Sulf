package git.immutabled.sulf.spigot

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 10:39
 */

const val BROADCAST_PID = "broadcast"
const val STAFF_PID = "staff_alert"
const val FREEZE_PID = "freeze"
const val ASSISTANCE_PID = "assistance"

val NO_CONSOLE_MESSAGE = Component.text("This command can only be executed in-game.", NamedTextColor.RED)

val NO_PERMISSION_MESSAGE = "${ChatColor.RED}You don't have permission to do this."

const val VANISH_METADATA_KEY = "vanish"
val VANISH_MESSAGE = "${ChatColor.RED}You cannot do this while vanished."

const val BUILD_METADATA_KEY = "build"

const val STAFF_METADATA_KEY = "staff"
val STAFF_MESSAGE = "${ChatColor.RED}You cannot do this while staff."

const val FREEZE_METADATA_KEY = "freeze"
val FREEZE_MESSAGE = "${ChatColor.RED}You cannot do this while frozen."

const val STAFF_CHAT_METADATA_KEY = "staff_chat"


const val STAFF_PERMISSION = "sulf.staff"
