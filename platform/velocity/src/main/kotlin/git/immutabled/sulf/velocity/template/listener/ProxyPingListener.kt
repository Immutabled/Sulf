package git.immutabled.sulf.velocity.template.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.api.proxy.server.ServerPing
import com.velocitypowered.api.proxy.server.ServerPing.SamplePlayer
import com.velocitypowered.api.util.Favicon
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import git.immutabled.moshi.MessageUtil
import git.immutabled.moshi.TimeUtil
import git.immutabled.sulf.velocity.SulfPlugin
import git.immutabled.sulf.velocity.TEMPLATE_TIME_PLACEHOLDER
import git.immutabled.sulf.velocity.proxy.ProxyModule
import git.immutabled.sulf.velocity.template.Template
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.whitelist.WhitelistModule
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 15:33
 */
class ProxyPingListener(
    private val plugin: SulfPlugin,
    private val module: TemplateModule,
    private val whitelist: WhitelistModule,
    private val proxies: ProxyModule
) {

    @Subscribe
    fun onProxyPing(event: ProxyPingEvent) {
        val builder = ServerPing.builder()

        builder.version(
            if (whitelist.config.whitelisted) {
                ServerPing.Version(9999, "● Whitelisted")
            } else {
                event.ping.version
            }
        )

        module.config.templates
            .find {
                it.name.equals(
                    if (whitelist.config.whitelisted) {
                        whitelist.config.postTemplate
                    } else {
                        module.config.currentTemplate
                    }, ignoreCase = true
                )
            }
            ?.let { template ->

                val hoverLines = if (whitelist.config.whitelisted) {
                    whitelist.config.whitelistedHover.map {
                        it.format(template)
                    }
                } else {
                    module.config.hover.map {
                        it.format(template)
                    }
                }

                val samplePlayers = hoverLines.map { line ->
                    SamplePlayer(line.replace("&", "§"), UUID.randomUUID())
                }.toTypedArray()

                val (firstLine, secondLine) = if (event.connection.protocolVersion.lessThan(ProtocolVersion.MINECRAFT_1_9)) {
                    template.legacyEntry
                } else {
                    template.entry
                }

                val serializer = if (event.connection.protocolVersion.lessThan(ProtocolVersion.MINECRAFT_1_9)) {
                    LegacyComponentSerializer.legacyAmpersand()
                } else {
                    MiniMessage.miniMessage()
                }

                val motd = serializer.deserialize(
                    MessageUtil.center(firstLine.format(template), 90)
                )
                    .append(Component.newline())
                    .append(serializer.deserialize(
                        MessageUtil.center(secondLine.format(template), 90)
                    ))


                val rootDir = Paths.get("").toAbsolutePath()
                val faviconFile = rootDir.resolve("server-icon.png")

                try {
                    if (faviconFile.exists()) {
                        val favicon = Favicon.create(faviconFile)
                        builder.favicon(favicon)
                    } else {
                        println("Favicon not found")
                    }
                } catch (e: Exception) {
                    plugin.logger.warn("Could not load favicon: ${e.message}")
                    plugin.logger.warn(faviconFile.toString())
                }

                builder
                    .onlinePlayers(proxies.total())
                    .maximumPlayers(proxies.maximum())
                    .description(motd)
                    .samplePlayers(*samplePlayers)
            }

        event.ping = builder.build()
    }

    fun String.format(template: Template) : String {
        val remainingTime = module.config.expiredTime - System.currentTimeMillis()
        val cooldown = if (remainingTime >= 0) {
            if (template.shortCooldown) {
                TimeUtil.formatIntoShortString(remainingTime)
            } else {
                TimeUtil.formatIntoDetailedString(remainingTime)
            }
        } else {
            template.expiredCooldown
        }

        return this.replace(TEMPLATE_TIME_PLACEHOLDER, cooldown)
            .replace("%total%", proxies.total().toString())
            .replace("%max%", proxies.maximum().toString())
    }
}