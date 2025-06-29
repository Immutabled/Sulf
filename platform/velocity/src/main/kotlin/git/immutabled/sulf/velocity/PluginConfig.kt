package git.immutabled.sulf.velocity

import git.immutabled.moshi.JsonConfig
import git.immutabled.sulf.velocity.proxy.ProxyInfo

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:04
 */
class PluginConfig : JsonConfig() {

    var identifier: String = "Sulf-01"
    var region: String = "NA"
    var maxPlayers: Int = 1000

    var suitables: List<String> = listOf(
        "hub-01",
        "hub-02",
        "hub-03",
        "hub-04",
        "hub-05",
    )

}