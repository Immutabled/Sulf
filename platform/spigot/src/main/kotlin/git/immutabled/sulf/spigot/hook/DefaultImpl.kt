package git.immutabled.sulf.spigot.hook

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

class DefaultImpl: CoreHook {

    override fun getDisplayName(player: Player, prefix: Boolean): TextComponent {
        return LegacyComponentSerializer.legacySection().deserialize(player.getDisplayName())
    }
}