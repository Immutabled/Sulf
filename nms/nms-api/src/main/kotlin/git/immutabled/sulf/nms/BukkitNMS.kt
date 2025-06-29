package git.immutabled.sulf.nms

import git.immutabled.sulf.nms.nms.LocalItemStack
import org.bukkit.inventory.ItemStack as BukkitItemStack

interface BukkitNMS {

    fun itemStack(custom: LocalItemStack): BukkitItemStack

}