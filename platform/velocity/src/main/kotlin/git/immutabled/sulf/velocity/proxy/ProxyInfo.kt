package git.immutabled.sulf.velocity.proxy

import java.util.UUID

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 13:00
 */

data class ProxyInfo (
    var name: String,
    var status: Status = Status.UNKNOWN,
    var port: Int,
    var region: String,
    var maxPlayers: Int,
    var players: MutableList<UUID> = mutableListOf()
)
