package git.immutabled.sulf.velocity.whitelist.listener

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.kickComponent
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.whitelist.Type
import git.immutabled.sulf.velocity.whitelist.WhitelistModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 09, 17:59
 */
class LoginListener(
    private val plugin: SulfPlugin,
    private val whitelist: WhitelistModule
) {

    @Subscribe(order = PostOrder.LAST)
    fun onPlayerLogin(event: LoginEvent) {
        if (whitelist.config.type == Type.RANK) return
        if (!whitelist.config.whitelisted) return
        if (whitelist.config.whitelistUsers.contains(event.player.username)) return

        plugin.server.eventManager.fire(
            StaffAlertEvent(
                Component.text()
                    .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
                    .append(Component.text(plugin.hook.getDisplayName(event.player)).color(NamedTextColor.WHITE))
                    .append(Component.text(" tried to login but was whitelisted!").color(NamedTextColor.RED))
                    .build()
            )
        )
        println("Kicking ${plugin.hook.getDisplayName(event.player)} by uuid whitelist")

        event.result = kickComponent(whitelist)
    }
}