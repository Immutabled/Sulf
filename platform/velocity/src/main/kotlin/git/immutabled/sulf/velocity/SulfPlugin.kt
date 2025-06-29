package git.immutabled.sulf.velocity

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer;
import git.immutabled.connector.Connector
import git.immutabled.moshi.ConfigUtil
import git.immutabled.sulf.velocity.hook.CoreHook
import git.immutabled.sulf.velocity.hook.DefaultImpl
import git.immutabled.sulf.velocity.hook.luckperms.LuckPermsImpl
import git.immutabled.sulf.velocity.proxy.ProxyModule
import git.immutabled.sulf.velocity.proxy.event.ProxyUpEvent
import git.immutabled.sulf.velocity.staff.StaffModule
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.template.listener.ProxyPingListener
import git.immutabled.sulf.velocity.whitelist.WhitelistModule
import org.slf4j.Logger;
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.nio.file.Path

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:04
 */
@Plugin(
    id = "sulf",
    name = "Sulf",
    version = "2025.0407.01-SNAPSHOT",
    url = "https://midesprojects.net",
    description = "Sulf is a plugin that allows you to manage your server with ease.",
    authors = ["Mides Dev Team"],
    dependencies = [Dependency(id = "luckperms", optional = true)]
)
class SulfPlugin @Inject
constructor(
    val server: ProxyServer,
    val logger: Logger,
    @DataDirectory val dataDirectory: Path
) {

    lateinit var config: PluginConfig
    private val modules = mutableListOf<PluginModule>()

    lateinit var connector: Connector

    var hook: CoreHook = DefaultImpl(this)

    lateinit var proxyModule: ProxyModule
    private lateinit var staffModule: StaffModule
    lateinit var templateModule: TemplateModule
    lateinit var whitelistModule: WhitelistModule
    private lateinit var jedis: JedisPool

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        instance = this

        this.config = ConfigUtil.register("config", "Sulf", this.dataDirectory.toFile(), PluginConfig())
        this.jedis = JedisPool(JedisPoolConfig(),"216.45.2.11",6379,2000,"Trjfza3SIKdXCnOF7zDHlu6mnJDml7",0)
        this.connector = Connector("Sulf", this.jedis)

        this.proxyModule = ProxyModule()
        this.staffModule = StaffModule()
        this.templateModule = TemplateModule()
        this.whitelistModule = WhitelistModule(this.templateModule)

        modules.add(this.proxyModule)
        modules.add(this.staffModule)
        modules.add(this.templateModule)
        modules.add(this.whitelistModule)

        try {
            val luckPermsPlugin = server.pluginManager.getPlugin("luckperms").orElse(null)
            if (luckPermsPlugin != null && luckPermsPlugin.instance.isPresent) {
                this.hook = LuckPermsImpl(whitelistModule)  // Pasamos el plugin completo
                this.logger.info("Successfully linked with LuckPerms")
            } else {
                this.hook = DefaultImpl(this)
                this.logger.info("LuckPerms not found, using default implementation")
            }
        } catch (e: NoClassDefFoundError) {
            this.hook = DefaultImpl(this)
            this.logger.warn("LuckPerms API not available, using default implementation")
        }

        this.modules.forEach {
            it.load(this)
            this.logger.info("Successfully loaded ${it.javaClass.simpleName}")
        }

        this.hook.load(this)
        this.server.eventManager.register(
            this,
            ProxyPingListener(this, this.templateModule, this.whitelistModule, this.proxyModule)
        )

        this.server.eventManager.fireAndForget(
            ProxyUpEvent()
        )
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        this.modules.forEach { it.unload(this) }
        ConfigUtil.save(config)
    }

    companion object {
        private lateinit var instance: SulfPlugin

        /**
         * Returns the plugin instance
         * @return the plugin instance
         */
        fun get(): SulfPlugin {
            return this.instance
        }
    }
}