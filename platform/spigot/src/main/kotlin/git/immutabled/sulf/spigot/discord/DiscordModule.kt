package git.immutabled.sulf.spigot.discord

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import git.immutabled.moshi.MoshiUtil
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.PluginModule
import git.immutabled.sulf.spigot.cross.placeholder.PlayersExpansion
import git.immutabled.sulf.spigot.discord.auth.AuthInterceptor
import git.immutabled.sulf.spigot.discord.placeholder.DiscordExpansion
import git.immutabled.sulf.spigot.discord.runnable.DiscordRunnable
import git.immutabled.sulf.spigot.discord.service.DiscordService
import okhttp3.OkHttpClient
import org.bukkit.command.CommandExecutor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 22, 19:56
 */
class DiscordModule : PluginModule {

    private lateinit var retrofit: Retrofit
    private lateinit var service: DiscordService
    private var runnable = git.immutabled.sulf.spigot.discord.runnable.DiscordRunnable(this)

    var online: Int = 0
    var members: Int = 0

    fun getService(): DiscordService {
        if (!this::service.isInitialized) {
            throw IllegalStateException("DiscordService not initialized. Call create() first.")
        }
        return this.service
    }

    override fun load(plugin: Plugin) {
        this.retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(MoshiUtil.instance))
            .baseUrl("https://canary.discord.com/api/v10/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(plugin.config.tokenBot))
                    .build()
            )
            .build()

        this.service = this.retrofit.create(DiscordService::class.java)
        this.runnable.runTaskTimerAsynchronously(plugin, 0L, 20 * 60 * 5)
    }

    override fun unload(plugin: Plugin) {

    }

    override fun commands(): Map<List<String>, CommandExecutor> {
        return emptyMap()
    }

    override fun expansions(plugin: Plugin): Set<PlaceholderExpansion> {
        return setOf(
            DiscordExpansion(
                plugin,
                this
            )
        )
    }
}