package git.immutabled.sulf.spigot.hook

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

class PlaceholderImpl : CoreHook {
    private val legacySerializer = LegacyComponentSerializer.legacySection()

    override fun getDisplayName(player: Player, prefix: Boolean): TextComponent {
        val placeholderString = PlaceholderAPI.setPlaceholders(
            player,
            "${if (prefix) "%vault_prefix%%luckperms_suffix%" else "%luckperms_suffix%"}${player.name}"
        )

        return legacySerializer.deserialize(placeholderString)
    }
}