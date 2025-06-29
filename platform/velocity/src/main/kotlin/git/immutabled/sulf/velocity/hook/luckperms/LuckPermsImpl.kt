package git.immutabled.sulf.velocity.hook.luckperms

import com.velocitypowered.api.proxy.Player
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.hook.CoreHook
import git.immutabled.sulf.velocity.whitelist.WhitelistModule


class LuckPermsImpl(
    val whitelist: WhitelistModule,
) : CoreHook {

    /**
     * LuckPerms API
     */
    var api: LuckPerms = LuckPermsProvider.get()


    override fun load(plugin: SulfPlugin) {
        plugin.server.eventManager.register(plugin, LuckPermsLoginListener(this,whitelist, plugin))
    }

    override fun getDisplayName(player: Player): String {
        val user = api.userManager.getUser(player.uniqueId) ?: throw IllegalStateException("User not found")
        val metaData = user.cachedData.metaData
        val suffix = metaData.suffix ?: ""

        return "$suffix${player.username}"
    }

    override fun getGroupDisplayName(group: String): String {
        val rank = api.groupManager.getGroup(group) ?: throw IllegalStateException("Group not found")
        return rank.displayName ?: group
    }

    override fun getGroupDisplayName(player: Player): String {
        val user = api.userManager.getUser(player.uniqueId) ?: throw IllegalStateException("User not found")
        val suffix = user.cachedData.metaData.suffix ?: ""
        val group = user.primaryGroup
        val rank = api.groupManager.getGroup(group) ?: throw IllegalStateException("Group not found")
        val displayName = rank.displayName ?: group

        return "$suffix$displayName"
    }


    override fun getRanks(): List<String> {
        return this.api.groupManager.loadedGroups.map { it.name }
    }
}