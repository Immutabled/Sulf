package git.immutabled.sulf.velocity.whitelist.connector

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.ConfigUtil
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.WHITELIST_CONFIG_PID
import git.immutabled.sulf.velocity.whitelist.Type
import git.immutabled.sulf.velocity.whitelist.event.WhitelistDisableEvent
import git.immutabled.sulf.velocity.whitelist.event.WhitelistEnableEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:18
 */
object WhitelistConfigPacketListener: PacketListener {

    @Data(id = WHITELIST_CONFIG_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val key = data.getString("id") ?: return

        when (key) {
            "whitelisted" -> {
                val value = data.getBoolean("value") ?: return

                if (value) {
                    SulfPlugin.get().server.eventManager.fireAndForget(
                        WhitelistDisableEvent()
                    )
                } else  {
                    SulfPlugin.get().server.eventManager.fireAndForget(
                        WhitelistEnableEvent()
                    )
                }
                SulfPlugin.get().whitelistModule.config.whitelisted = value
            }
            "whitelistUsers" -> {
                val list = data.getJsonObject("value").get().values.filterIsInstance<String>()
                val value = list.toMutableList()

                SulfPlugin.get().whitelistModule.config.whitelistUsers = value
            }
            "whitelistRank" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("Whitelist rank is not a string")

                SulfPlugin.get().whitelistModule.config.whitelistRank = value
            }
            "whitelistedHover" -> {
                val list = data.getJsonObject("value").get().values.filterIsInstance<String>()
                val value = list.toList()

                SulfPlugin.get().whitelistModule.config.whitelistedHover = value
            }
            "whitelistKickMessage" -> {
                val list = data.getJsonObject("value").get().values.filterIsInstance<String>()
                val value = list.toList()

                SulfPlugin.get().whitelistModule.config.whitelistKickMessage = value
            }
            "template" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("Post template is not a string")

                SulfPlugin.get().whitelistModule.config.postTemplate = value
            }
            "fallback" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("Fallback template is not a string")

                SulfPlugin.get().whitelistModule.config.fallbackTemplate = value
            }
            "type" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("Whitelist type is not a string")
                SulfPlugin.get().whitelistModule.config.type = Type.valueOf(value)
            }
        }

        ConfigUtil.save(SulfPlugin.get().whitelistModule.config)
    }
}