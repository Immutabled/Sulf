package git.immutabled.sulf.velocity.hook.luckperms

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.luckperms.api.query.Flag
import net.luckperms.api.query.QueryMode
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.kickComponent
import git.immutabled.sulf.velocity.staff.event.StaffAlertEvent
import git.immutabled.sulf.velocity.whitelist.Type
import git.immutabled.sulf.velocity.whitelist.WhitelistModule

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 20:12
 */
class LuckPermsLoginListener(
    val luckperms: LuckPermsImpl,
    val whitelist: WhitelistModule,
    val plugin: SulfPlugin,
) {
    @Subscribe(order = PostOrder.FIRST)
    fun onLogin(event: LoginEvent) {
        if (whitelist.config.type == Type.PLAYER) return

        if (whitelist.config.whitelistRank == "clear") return
        if (!whitelist.config.whitelisted) return
        if (whitelist.config.whitelistUsers.contains(event.player.username)) return

        val user = luckperms.api.userManager.getUser(event.player.uniqueId) ?: run {
            return
        }

        val requiredGroup = whitelist.config.whitelistRank

        val queryOptions = luckperms.api.contextManager.queryOptionsBuilder(QueryMode.CONTEXTUAL)
            .flag(Flag.RESOLVE_INHERITANCE, true)
            .build()

        val requiredWeight = luckperms.api.groupManager.getGroup(requiredGroup)
            ?.cachedData?.getMetaData(queryOptions)?.getMetaValue("weight")
            ?.toIntOrNull() ?: 0

        val userGroupsWithWeights = user.getInheritedGroups(queryOptions).mapNotNull { group ->
            val weight = group.cachedData.getMetaData(queryOptions).getMetaValue("weight")?.toIntOrNull() ?: 0
            group.name to weight
        }

        val primaryGroup = user.primaryGroup
        val primaryGroupWeight = luckperms.api.groupManager.getGroup(primaryGroup)
            ?.cachedData?.getMetaData(queryOptions)?.getMetaValue("weight")
            ?.toIntOrNull() ?: 0

        val allGroupsWithWeights = userGroupsWithWeights + (primaryGroup to primaryGroupWeight)
        val highestWeight = allGroupsWithWeights.maxOfOrNull { it.second } ?: 0

        if (highestWeight >= requiredWeight) {
            return
        }

        event.result = kickComponent(whitelist)

            plugin.server.eventManager.fire(
                StaffAlertEvent(
                    Component.text()
                        .append(Component.text("[Staff] ").color(NamedTextColor.BLUE))
                        .append(Component.text(plugin.hook.getDisplayName(event.player)).color(NamedTextColor.WHITE))
                        .append(Component.text(" tried to login but was blocked by whitelist!").color(NamedTextColor.RED))
                        .build()
                )
            )
    }
}