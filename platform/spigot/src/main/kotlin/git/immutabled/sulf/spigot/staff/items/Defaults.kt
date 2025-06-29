package git.immutabled.sulf.spigot.staff.items

import git.immutabled.sulf.api.events.staff.PlayerVanishEvent
import git.immutabled.sulf.nms.nms.LocalItemStack
import git.immutabled.sulf.spigot.Plugin
import git.immutabled.sulf.spigot.STAFF_METADATA_KEY
import git.immutabled.sulf.spigot.VANISH_METADATA_KEY
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 11:52
 */

val DEFAULT_STAFF_ITEMS = listOf(

    //compass
    object : StaffItem() {
        override fun getItem(player: Player): ItemStack {
            val item = Plugin.nms.itemStack(
                LocalItemStack(
                    material = Material.COMPASS,
                    displayName = "${ChatColor.LIGHT_PURPLE}Compass"
                )
            )

            return item
        }

        override fun getSlot(): Int {
            return 0
        }

        override fun onClick(player: Player, item: ItemStack) {
        }

        override fun onEntityInteract(event: PlayerInteractEntityEvent) {
        }
    },

    //freeze
    object : StaffItem() {
        override fun getItem(player: Player): ItemStack {
            val item = Plugin.nms.itemStack(
                LocalItemStack(
                    material = Material.PACKED_ICE,
                    displayName = "${ChatColor.LIGHT_PURPLE}Freeze"
                )
            )
            return item
        }

        override fun getSlot(): Int {
            return 4
        }

        override fun onClick(player: Player, item: ItemStack) {
            player.sendMessage("${ChatColor.RED}You must to click on a player to freeze them.")
        }

        override fun onEntityInteract(event: PlayerInteractEntityEvent) {
            event.player.performCommand("freeze ${event.rightClicked.name}")
        }

    },

    //invsee
    object : StaffItem() {
        override fun getItem(player: Player): ItemStack {
            val item = Plugin.nms.itemStack(
                LocalItemStack(
                    material = Material.BOOK,
                    displayName = "${ChatColor.LIGHT_PURPLE}Invsee"
                )
            )
            return item
        }

        override fun getSlot(): Int {
            return 1
        }

        override fun onClick(player: Player, item: ItemStack) {
        }

        override fun onEntityInteract(event: PlayerInteractEntityEvent) {
            event.player.performCommand("invsee ${event.rightClicked.name}")
        }
    },

    //random teleport
    object : StaffItem() {
        override fun getItem(player: Player): ItemStack {
            val item = Plugin.nms.itemStack(
                LocalItemStack(
                    material = Material.SLIME_BALL,
                    displayName = "${ChatColor.LIGHT_PURPLE}Random Teleport"
                )
            )
            return item
        }

        override fun getSlot(): Int {
            return 6
        }

        override fun onClick(player: Player, item: ItemStack) {
            Bukkit.getServer().onlinePlayers
                .filter { !it.hasMetadata(STAFF_METADATA_KEY) }
                .shuffled()
                .forEach { player.teleport(it.location) }
        }

        override fun onEntityInteract(event: PlayerInteractEntityEvent) {
        }
    },

    //staff online
    object : StaffItem() {
        override fun getItem(player: Player): ItemStack {
            val item = Plugin.nms.itemStack(
                LocalItemStack(
                    material = Material.PLAYER_HEAD,
                    displayName = "${ChatColor.LIGHT_PURPLE}Staff Online"
                )
            )
            return item
        }

        override fun getSlot(): Int {
            return 7
        }

        override fun onClick(player: Player, item: ItemStack) {
        }

        override fun onEntityInteract(event: PlayerInteractEntityEvent) {

        }
    },

    //vanish
    object : StaffItem() {
        override fun getItem(player: Player): ItemStack {
            var dye: Material = Material.GRAY_DYE
            var name = "${ChatColor.GRAY}Vanish"

            if (player.hasMetadata(VANISH_METADATA_KEY)) {
                dye = Material.GREEN_DYE
                name = "${ChatColor.GREEN}Un-Vanish"
            }

            val item = Plugin.nms.itemStack(
                LocalItemStack(
                    material = dye,
                    displayName = name
                )
            )
            return item
        }

        override fun getSlot(): Int {
            return 8
        }

        override fun onClick(player: Player, item: ItemStack) {
            Bukkit.getServer().pluginManager.callEvent(PlayerVanishEvent(player))
            player.inventory.setItem(this.getSlot(), this.getItem(player))
            player.updateInventory()
        }

        override fun onEntityInteract(event: PlayerInteractEntityEvent) {

        }
    },
)