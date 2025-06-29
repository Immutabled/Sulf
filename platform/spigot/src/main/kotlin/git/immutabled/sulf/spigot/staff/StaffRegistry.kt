package git.immutabled.sulf.spigot.staff

import org.bukkit.inventory.ItemStack
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 05, 13:02
 */
class StaffRegistry {
    val online: MutableMap<UUID, String> = ConcurrentHashMap()
    val inventory: MutableMap<UUID, Array<ItemStack?>> = ConcurrentHashMap()
}