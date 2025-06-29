package git.immutabled.sulf.velocity.proxy.connector

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.ConfigUtil
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.velocity.PROXY_UPDATE_PID
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.proxy.ProxyInfo
import git.immutabled.sulf.velocity.proxy.Status
import java.util.UUID

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:31
 */
object ProxyUpdatePacketListener : PacketListener {

    @Data(id = PROXY_UPDATE_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val key = data.getString("key") ?: return
        val id = data.getString("id") ?: return

        when (key) {
            "added" -> {
                val proxyData = data.getJsonObject("value") ?: return

                val name = proxyData.getString("name") ?: ""
                val port = proxyData.getInt("port") ?: 0
                val region = proxyData.getString("region") ?: ""
                val rawPlayers = proxyData["players"]
                val maxPlayers = proxyData.getInt("maxPlayers") ?: return

                val players = when (rawPlayers) {
                    is List<*> -> rawPlayers.mapNotNull {
                        try {
                            UUID.fromString(it.toString())
                        } catch (e: IllegalArgumentException) {
                            null
                        }
                    }.toMutableList()
                    else -> mutableListOf()
                }

                val existingProxy = SulfPlugin.get().proxyModule.config.proxies.find {
                    it.name.equals(name, true)
                }

                if (existingProxy != null) {
                    existingProxy.apply {
                        status = Status.ONLINE
                        this.port = port
                        this.region = region
                        this.players = players.toMutableList()
                        this.maxPlayers = maxPlayers
                    }
                } else {
                    SulfPlugin.get().proxyModule.config.proxies.add(
                        ProxyInfo(
                            name = name,
                            port = port,
                            region = region,
                            players = players,
                            maxPlayers = maxPlayers,
                            status = Status.ONLINE
                        )
                    )
                }
            }
            "shutdown" -> {
                SulfPlugin.get().proxyModule.config.proxies.find { it.name.equals(id, true) }?.let { proxy ->
                    proxy.status = Status.OFFLINE
                    proxy.players.clear()
                }
            }
            "players" -> {
                val uuid = try {
                    UUID.fromString(data.getString("value") ?: return)
                } catch (ex: IllegalArgumentException) {
                    return
                }

                SulfPlugin.get().proxyModule.config.proxies.find { it.name.equals(id, true) }?.let { proxy ->
                    if (proxy.players.contains(uuid)) {
                        proxy.players.remove(uuid)
                    } else {
                        proxy.players.add(uuid)
                    }
                }
            }
        }
        ConfigUtil.save(SulfPlugin.get().proxyModule.config)
    }
}