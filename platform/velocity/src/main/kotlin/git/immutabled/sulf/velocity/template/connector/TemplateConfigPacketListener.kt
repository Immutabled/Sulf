package git.immutabled.sulf.velocity.template.connector

import git.immutabled.connector.Data
import git.immutabled.connector.listener.PacketListener
import git.immutabled.moshi.ConfigUtil
import git.immutabled.moshi.json.JsonObject
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.TEMPLATE_CONFIG_PID
import git.immutabled.sulf.velocity.template.Template

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 10:18
 */
object TemplateConfigPacketListener : PacketListener {

    @Data(id = TEMPLATE_CONFIG_PID, forceLocal = true)
    fun onStaffAlertEvent(data: JsonObject) {
        val key = data.getString("key") ?: return

        when (key) {
            "create" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")

                SulfPlugin.get().templateModule.config.templates.add(
                    Template(value)
                )
            }

            "delete" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")

                SulfPlugin.get().templateModule.config.templates.removeIf { it.name == value }
            }

            "set" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")

                SulfPlugin.get().templateModule.config.templates.find { it.name == value }.let {
                    if (it == null) throw IllegalArgumentException("Template not found")
                    SulfPlugin.get().templateModule.config.currentTemplate = it.name
                }

            }

            "normal_first" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")
                val identifier = data.getString("identifier") ?: throw IllegalArgumentException("No identifier provided")

                SulfPlugin.get().templateModule.config.templates.find { it.name == identifier }.let {
                    if (it == null) throw IllegalArgumentException("Template not found")

                    it.entry = value to it.entry.second
                }
            }

            "normal_second" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")
                val identifier = data.getString("identifier") ?: throw IllegalArgumentException("No identifier provided")

                SulfPlugin.get().templateModule.config.templates.find { it.name == identifier }.let {
                    if (it == null) throw IllegalArgumentException("Template not found")

                    it.entry = it.entry.first to value
                }
            }
            "legacy_first" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")
                val identifier = data.getString("identifier") ?: throw IllegalArgumentException("No identifier provided")

                SulfPlugin.get().templateModule.config.templates.find { it.name == identifier }.let {
                    if (it == null) throw IllegalArgumentException("Template not found")

                    it.legacyEntry = value to it.legacyEntry.second
                }
            }

            "legacy_second" -> {
                val value = data.getString("value") ?: throw IllegalArgumentException("No value provided")
                val identifier = data.getString("identifier") ?: throw IllegalArgumentException("No identifier provided")

                SulfPlugin.get().templateModule.config.templates.find { it.name == identifier }.let {
                    if (it == null) throw IllegalArgumentException("Template not found")

                    it.legacyEntry = it.legacyEntry.first to value
                }
            }

            "time" -> {
                val value = data.getLong("value") ?: throw IllegalArgumentException("No value provided")
                val identifier = data.getString("identifier") ?: throw IllegalArgumentException("No identifier provided")

                SulfPlugin.get().templateModule.config.templates.find { it.name == identifier }.let {
                    if (it == null) throw IllegalArgumentException("Template not found")

                    it.cooldown = value
                    SulfPlugin.get().templateModule.config.expiredTime = System.currentTimeMillis() + value
                }
            }
        }

        ConfigUtil.save(SulfPlugin.get().templateModule.config)
    }
}