package git.immutabled.sulf.spigot.discord.runnable

import git.immutabled.sulf.spigot.discord.DiscordModule
import org.bukkit.scheduler.BukkitRunnable

class DiscordRunnable(
    private val module: DiscordModule
) : BukkitRunnable() {

    override fun run() {
        try {
            val response = this.module.getService().getGuildInfo(1321457377513574421).execute()

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    body["approximate_presence_count"]?.let {
                        module.online = (it as Number).toInt()
                    }
                    body["approximate_member_count"]?.let {
                        this.module.members = (it as Number).toInt()
                    }
                }
            } else {
                throw IllegalStateException("Error loading guild info from Discord API: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}