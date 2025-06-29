package git.immutabled.sulf.spigot.assistance

import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since March 24, 05:20
 */
class AssistanceRegistry {
    private val cooldowns: MutableMap<UUID, Long> = ConcurrentHashMap()

    fun addReport(uuid: UUID) {
        cooldowns[uuid] = System.currentTimeMillis()
    }

    fun getCooldown(player: Player): Long {
        val lastReport = cooldowns[player.uniqueId] ?: return 0
        val elapsed = System.currentTimeMillis() - lastReport
        return COOLDOWN_TIME - elapsed
    }

    fun hasCooldown(player: Player): Boolean {
        return getCooldown(player) > 0
    }


    companion object {
        val COOLDOWN_TIME = TimeUnit.MINUTES.toMillis(2) // 2 minute cooldown
    }
}