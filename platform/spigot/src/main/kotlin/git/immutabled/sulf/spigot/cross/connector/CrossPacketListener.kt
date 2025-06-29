package git.immutabled.sulf.spigot.cross.connector

import git.immutabled.connector.cross.CROSS_PID
import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.spigot.Plugin

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 22, 19:14
 */
class CrossPacketListener(
    private val plugin: Plugin
) : PacketListener {

    @Data(id = CROSS_PID)
    fun cross(json: JsonObject) {
        val players = json.getInt("players") ?: throw IllegalArgumentException("Players not found")
        this.plugin.players = players

        val slots = json.getInt("slots") ?: throw IllegalArgumentException("Slots not found")
        this.plugin.slots = slots
    }
}