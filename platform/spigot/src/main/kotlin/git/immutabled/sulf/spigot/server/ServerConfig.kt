package git.immutabled.sulf.spigot.server

import git.immutabled.moshi.JsonConfig

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 26, 21:42
 */
class ServerConfig : JsonConfig() {

    val servers: MutableList<ServerInfo> = mutableListOf()
}