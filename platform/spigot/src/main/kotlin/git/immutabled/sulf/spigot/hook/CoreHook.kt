package git.immutabled.sulf.spigot.hook

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 23:32
 */
interface CoreHook {

    fun getDisplayName(player: Player, prefix: Boolean): TextComponent

    fun getDisplayNameString(player: Player, prefix: Boolean): String {
        return LegacyComponentSerializer.legacySection().serialize(getDisplayName(player, prefix))
    }
}