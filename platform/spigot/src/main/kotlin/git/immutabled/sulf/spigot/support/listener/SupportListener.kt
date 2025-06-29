package git.immutabled.sulf.spigot.support.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import git.immutabled.sulf.spigot.support.SupportModule
import git.immutabled.sulf.spigot.support.event.SupportEvent
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 13, 23:58
 */
class SupportListener(
    private val module: SupportModule
): Listener {


    @EventHandler
    fun onPlayerSupport(event: SupportEvent) {
        module.config.claims.add(event.player.uniqueId)
        module.config.commands.forEach(event.player::performCommand)
        module.config.messages.forEach(event.player::sendMessage)
    }
}