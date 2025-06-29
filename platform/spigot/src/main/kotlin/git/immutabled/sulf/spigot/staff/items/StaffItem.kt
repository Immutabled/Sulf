package git.immutabled.sulf.spigot.staff.items

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 11:50
 */
abstract class StaffItem {

    abstract fun getItem(player: Player): ItemStack

    abstract fun getSlot(): Int

    abstract fun onClick(player: Player, item: ItemStack)

    abstract fun onEntityInteract(event: PlayerInteractEntityEvent)
}