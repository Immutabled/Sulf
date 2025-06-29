package git.immutabled.sulf.nms.paper

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import git.immutabled.sulf.nms.BukkitNMS
import git.immutabled.sulf.nms.nms.LocalItemStack
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 29, 13:55
 */
class PaperNMS : BukkitNMS {
    override fun itemStack(custom: LocalItemStack): ItemStack {
        val itemStack = ItemStack(custom.material, custom.amount)

        itemStack.editMeta { meta ->
            custom.displayName?.let {
                meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(it))
            }

            if (custom.lore.isNotEmpty()) {
                meta.lore(custom.lore.map { line ->
                    LegacyComponentSerializer.legacyAmpersand().deserialize(line)
                })
            }
        }

        return itemStack
    }
}
