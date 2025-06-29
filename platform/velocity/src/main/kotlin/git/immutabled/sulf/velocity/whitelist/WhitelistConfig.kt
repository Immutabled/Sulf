package git.immutabled.sulf.velocity.whitelist

import git.immutabled.moshi.JsonConfig

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 09:57
 */
open class WhitelistConfig : JsonConfig() {

    var whitelisted = false

    var type: Type = Type.PLAYER

    var whitelistUsers = mutableListOf(
        "Inmutable",
        "Kohiii",
        "Tryfast",
        "thatsmybaby"
    )

    var postTemplate: String = "whitelisted"
    var fallbackTemplate: String = "default"

    var whitelistRank: String = "owner"

    var whitelistedHover: List<String> = mutableListOf(
        "This is a hover",
        "This is another hover"
    )

    var whitelistKickMessage: List<String> = mutableListOf(
        "You are not whitelisted!",
        "Please whitelist yourself!"
    )
}