package git.immutabled.sulf.nms.nms

import org.bukkit.Material

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 29, 13:20
 */
data class LocalItemStack(
    var material: Material,
    var amount: Int = 1,
    var displayName: String? = null,
    var lore: MutableList<String> = mutableListOf(),
)