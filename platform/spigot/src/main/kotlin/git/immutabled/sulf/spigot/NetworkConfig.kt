package git.immutabled.sulf.spigot

import com.squareup.moshi.JsonClass

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since junio 28, 23:16
 */
@JsonClass(generateAdapter = true)
class NetworkConfig {
    var name: String = "Example Network"
    var discord: String = "discord.gg/Example"
    var instagram: String = "instagram.com/Example"
    var store: String = "store.example.com"
    var tiktok: String = "tiktok.com/Example"
    var twitter: String = "twitter.com/Example"
    var votes: String = "votes.example.com"
    var website: String = "www.example.com"
}
