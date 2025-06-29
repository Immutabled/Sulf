package git.immutabled.sulf.nms.purpur

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import git.immutabled.sulf.nms.BukkitNMS
import git.immutabled.sulf.nms.nms.LocalItemStack
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 29, 13:56
 */
class PurpurNMS : BukkitNMS {
    override fun itemStack(custom: LocalItemStack): ItemStack {
        val itemStack = ItemStack(custom.material, custom.amount)
        val meta: ItemMeta = itemStack.itemMeta

        itemStack.setDisplayName(custom.displayName)
//
//        itemStack.editMeta { meta ->
//            custom.displayName?.let {
//                meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(it))
//            }
//        }

        if (custom.lore.isNotEmpty()) {
            meta.lore(custom.lore.map { line ->
                LegacyComponentSerializer.legacyAmpersand().deserialize(line)
            })
        }

        itemStack.itemMeta = meta
        return itemStack
    }
}
