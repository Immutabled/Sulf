package git.immutabled.sulf.spigot

import git.immutabled.moshi.JsonConfig

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 19:30
 */
class PluginConfig : JsonConfig() {

    /**
     * The server name
     */
    var serverName: String = "Unknown Server"

    var tokenBot: String = ""

    var network: NetworkConfig = NetworkConfig()
}
