package git.immutabled.sulf.spigot.server

import com.squareup.moshi.JsonClass

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 11:46
 */
@JsonClass(generateAdapter = true)
data class ServerInfo(
    var name: String,
    var port: Int,
    var status: Status = Status.OFFLINE,
    var players: Int = 0,
    var maxPlayers: Int = 0,
    var metadata: Map<String, String> = mapOf(),
    var up: Long = 0,
    var uptime: Long = 0,
    var onlineMode: Boolean = false,
    var bukkitVersion: String = ""
)