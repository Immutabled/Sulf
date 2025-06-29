package git.immutabled.sulf.spigot.support.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import git.immutabled.sulf.spigot.support.SupportModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 14, 18:52
 */

class PlayerJoinListener(
    private val module: SupportModule
) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!module.config.enabled) return
        if (module.config.claims.contains(event.player.uniqueId)) return

        val mensaje = Component.text()
            .append(Component.text("¡Hey! ", NamedTextColor.GOLD, TextDecoration.BOLD))
            .append(Component.text("Aún no has reclamado tu recompensa de ", NamedTextColor.YELLOW))
            .append(Component.text("Partner", NamedTextColor.AQUA, TextDecoration.BOLD))
            .append(Component.text(". ", NamedTextColor.YELLOW))
            .append(
                Component.text("[RECLAMAR]", NamedTextColor.GREEN, TextDecoration.BOLD)
                    .hoverEvent(HoverEvent.showText(Component.text("¡Haz clic para reclamar!", NamedTextColor.GRAY)))
                    .clickEvent(ClickEvent.runCommand("/support"))
            )
            .append(Component.text(" ¿Qué estás esperando?", NamedTextColor.YELLOW))
            .build()

        event.player.sendMessage(mensaje)
    }
}