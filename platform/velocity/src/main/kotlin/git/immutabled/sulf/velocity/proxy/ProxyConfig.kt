package git.immutabled.sulf.velocity.proxy

import git.immutabled.moshi.JsonConfig

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 21, 03:18
 */
class ProxyConfig : JsonConfig() {

    var proxies: MutableList<ProxyInfo> = mutableListOf()
}