package git.immutabled.sulf.spigot.assistance

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageListener
import java.util.*
import java.util.concurrent.CompletableFuture

class VelocityBridge(private val plugin: JavaPlugin) : PluginMessageListener {

    private val serverResponses = mutableMapOf<UUID, CompletableFuture<String>>()

    init {
        plugin.server.messenger.registerOutgoingPluginChannel(plugin, "BungeeCord")
        plugin.server.messenger.registerIncomingPluginChannel(plugin, "BungeeCord", this)
    }

    /**
     * Obtiene el servidor actual del jugador
     */
    fun getPlayerServer(player: Player): CompletableFuture<String> {
        val future = CompletableFuture<String>()
        serverResponses[player.uniqueId] = future

        try {
            val out = ByteStreams.newDataOutput().apply {
                writeUTF("GetServer")
            }
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
        } catch (e: Exception) {
            future.complete("unknown")
            serverResponses.remove(player.uniqueId)
        }

        return future
    }

    /**
     * Envía un jugador a otro servidor (similar a tu ServerUtil)
     */
    fun sendToServer(player: Player, server: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()

        try {
            val out = ByteStreams.newDataOutput().apply {
                writeUTF("ConnectOther")
                writeUTF(player.name)
                writeUTF(server)
            }
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
            future.complete(true)
        } catch (e: Exception) {
            future.complete(false)
        }

        return future
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") return

        val input = ByteStreams.newDataInput(message)
        when (input.readUTF()) {
            "GetServer" -> {
                val serverName = input.readUTF()
                serverResponses[player.uniqueId]?.complete(serverName)
                serverResponses.remove(player.uniqueId)
            }
        }
    }
}