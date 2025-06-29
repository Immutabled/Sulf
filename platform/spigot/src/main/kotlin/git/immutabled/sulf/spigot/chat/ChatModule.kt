package git.immutabled.sulf.spigot.chat

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.chat.commands.ClearChatCommand
import git.immutabled.sulf.spigot.chat.commands.MuteChatCommand
import git.immutabled.sulf.spigot.chat.commands.SlowChatCommand
import git.immutabled.sulf.spigot.chat.listener.AsyncPlayerChatListener
import org.bukkit.command.CommandExecutor
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 17:47
 */
class ChatModule : PluginModule {

    /**
     * The last message time
     */
    private val lastMessage: Cache<UUID, Long> = CacheBuilder.newBuilder()
        .expireAfterWrite(5L, TimeUnit.MINUTES)
        .build()

    /**
     * If the chat is muted
     */
    var muted: Boolean = false

    /**
     * If the chat is slowed
     */
    var slowed: Boolean = false

    /**
     * The time in milliseconds that a player has to wait before sending another message
     */
    var slowTime: Long = 0L

    /**
     * The default time in milliseconds that a player has to wait before sending another message
     */
    var defaultSlowTime: Long = 5000L


    /**
     * Get the last message time
     *
     * @param uuid The player uuid
     * @return The last message time
     */
    fun getLastMessageAgo(uuid: UUID): Long {
        val lastMessageTime: Long = lastMessage.getIfPresent(uuid) ?: return 0L

        return System.currentTimeMillis() - lastMessageTime
    }

    /**
     * Set the last message time
     *
     * @param uuid The player uuid
     * @param time The last message time
     */
    fun setLastMessage(uuid: UUID, time: Long) {
        lastMessage.put(uuid, time)
    }

    override fun load(plugin: Plugin) {
        plugin.server.pluginManager.registerEvents(AsyncPlayerChatListener(this), plugin)
    }

    override fun unload(plugin: Plugin) {
    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return mapOf(
            listOf("slowchat") to SlowChatCommand(this),
            listOf("mutechat") to MuteChatCommand(this),
            listOf("clearchat") to ClearChatCommand()
        )
    }
}