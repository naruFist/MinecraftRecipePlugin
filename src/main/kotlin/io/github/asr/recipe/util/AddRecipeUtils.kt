package io.github.asr.recipe.util

import io.github.asr.kape.KapeTextColor
import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.Plugin


fun Plugin.add2x2recipe(a: ItemStack?, b: ItemStack?,
                        c: ItemStack?, d: ItemStack?,
                        result: ItemStack): ShapedRecipe {
    var shape1 = "ab"
    var shape2 = "cd"

    if (a == null) shape1 = shape1.replace('a', ' ')
    if (b == null) shape1 = shape1.replace('b', ' ')
    if (c == null) shape2 = shape2.replace('c', ' ')
    if (d == null) shape2 = shape2.replace('d', ' ')

    return ShapedRecipe(NamespacedKey(this, "$number"),
        result).shape(shape1, shape2).setIngredientNotAir('a', a)
        .setIngredientNotAir('b', b).setIngredientNotAir('c', c)
        .setIngredientNotAir('d', d)
}

fun Plugin.add3x3recipe(a: ItemStack?, b: ItemStack?, c: ItemStack?,
                        d: ItemStack?, e: ItemStack?, f: ItemStack?,
                        g: ItemStack?, h: ItemStack?, i: ItemStack?,
                        result: ItemStack): ShapedRecipe {
    var shape1 = "abc"
    var shape2 = "def"
    var shape3 = "ghi"

    if (a == null) shape1 = shape1.replace('a', ' ')
    if (b == null) shape1 = shape1.replace('b', ' ')
    if (c == null) shape1 = shape1.replace('c', ' ')
    if (d == null) shape2 = shape2.replace('d', ' ')
    if (e == null) shape2 = shape2.replace('e', ' ')
    if (f == null) shape2 = shape2.replace('f', ' ')
    if (g == null) shape3 = shape3.replace('g', ' ')
    if (h == null) shape3 = shape3.replace('h', ' ')
    if (i == null) shape3 = shape3.replace('i', ' ')

    return ShapedRecipe(NamespacedKey(this, "$number"),
        result).shape(shape1, shape2, shape3).setIngredientNotAir('a', a)
        .setIngredientNotAir('b', b).setIngredientNotAir('c', c)
        .setIngredientNotAir('d', d).setIngredientNotAir('e', e)
        .setIngredientNotAir('f', f).setIngredientNotAir('g', g)
        .setIngredientNotAir('h', h).setIngredientNotAir('i', i)
}


fun Player.openAddRecipeGUI(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        slot(10, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("1 X 1").color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVer1(plugin)
        }

        slot(12, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("2 X 2").color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVer2(plugin)
        }

        slot(14, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("3 X 3").color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVer3(plugin)
        }

        slot(16, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("SHAPELESS").color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVerShapeless(plugin)
        }
    }
}

private fun Player.openAddRecipeGUIVer1(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (slotNumber != 12 && slotNumber != recipeResult)
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                it.displayName(Component.text("Add Recipe")
                    .color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.getItem(recipeResult) == null) {
                this@openAddRecipeGUIVer1.sendMessage(Component.text(
                    "You can't add recipe that result is AIR!"
                ).color(KapeTextColor.RED.toTextColor()))
            }

            if (inv.getItem(12) == null) {
                this@openAddRecipeGUIVer1.sendMessage(Component.text("Recipe must not be added by only AIR!")
                    .color(KapeTextColor.RED.toTextColor()))
                return@slot
            }

            val shapedRecipe = ShapedRecipe(NamespacedKey(plugin, "$number"),
                inv.getItem(recipeResult)!!).shape("a").setIngredientNotAir('a', inv.getItem(12))

            plugin.server.addRecipe(shapedRecipe)

            val type = 1
            recipeConfig.set("$number.type", type)
            recipeConfig.set("$number.recipe.0", inv.getItem(12))
            recipeConfig.set("$number.result", inv.getItem(recipeResult))

            number++

            this@openAddRecipeGUIVer1.closeInventory()

            recipeConfig.save(recipeListFile)
        }
    }
}

private fun Player.openAddRecipeGUIVer2(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (!recipeList.contains(slotNumber) && recipeResult != slotNumber ||
                (slotNumber == 4 || slotNumber == 13 || slotNumber > recipeResult))
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                it.displayName(Component.text("Add Recipe")
                    .color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.getItem(recipeResult) == null) {
                this@openAddRecipeGUIVer2.sendMessage(Component.text(
                    "You can't add recipe that result is AIR!"
                ).color(KapeTextColor.RED.toTextColor()))
            }


            if (inv.getItem(2) == null && inv.getItem(3) == null
                && inv.getItem(11) == null && inv.getItem(12) == null) {
                this@openAddRecipeGUIVer2.sendMessage(Component.text("Recipe must not be added by only AIR!")
                    .color(KapeTextColor.RED.toTextColor()))
                return@slot
            }

            plugin.server.addRecipe(plugin.add2x2recipe(inv.getItem(2), inv.getItem(3), inv.getItem(11), inv.getItem(12), inv.getItem(15)!!))

            val type = 2
            recipeConfig.set("$number.type", type)
            recipeConfig.set("$number.recipe.0", inv.getItem(recipeList[0]))
            recipeConfig.set("$number.recipe.1", inv.getItem(recipeList[1]))
            recipeConfig.set("$number.recipe.2", inv.getItem(recipeList[3]))
            recipeConfig.set("$number.recipe.3", inv.getItem(recipeList[4]))
            recipeConfig.set("$number.result", inv.getItem(recipeResult))

            number++

            recipeConfig.save(recipeListFile)

            this@openAddRecipeGUIVer2.closeInventory()
        }
    }
}

private fun Player.openAddRecipeGUIVer3(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (!recipeList.contains(slotNumber) && recipeResult != slotNumber)
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                it.displayName(Component.text("Add Recipe")
                    .color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.getItem(recipeResult) == null) {
                this@openAddRecipeGUIVer3.sendMessage(Component.text(
                    "You can't add recipe that result is AIR!"
                ).color(KapeTextColor.RED.toTextColor()))
            }

            recipeList.forEach {
                if (inv.getItem(it) != null) {

                    plugin.server.addRecipe(
                        plugin.add3x3recipe(inv.getItem(2),inv.getItem(3),inv.getItem(4),
                            inv.getItem(11),inv.getItem(12),inv.getItem(13),
                            inv.getItem(20),inv.getItem(21),inv.getItem(22),
                            inv.getItem(recipeResult)!!)
                    )

                    val type = 3
                    recipeConfig.set("$number.type", type)
                    for (j in 0 until type * type) {
                        recipeConfig.set("$number.recipe.$j", inv.getItem(recipeList[j]))
                    }
                    recipeConfig.set("$number.result", inv.getItem(recipeResult))

                    number++

                    recipeConfig.save(recipeListFile)

                    this@openAddRecipeGUIVer3.closeInventory()

                    return@slot
                }
            }

            this@openAddRecipeGUIVer3.sendMessage(Component.text("Recipe must not be added by only AIR!")
                .color(KapeTextColor.RED.toTextColor()))
        }
    }
}

private fun Player.openAddRecipeGUIVerShapeless(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (!recipeList.contains(slotNumber) && recipeResult != slotNumber)
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                displayName(Component.text("Add Recipe")
                    .color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.getItem(15) == null) {
                this@openAddRecipeGUIVerShapeless.sendMessage(Component.text(
                    "You can't add recipe that result is AIR!"
                ).color(KapeTextColor.RED.toTextColor()))
            }

            recipeList.forEach {
                if (inv.getItem(it) != null) {
                    val shapelessRecipe = ShapelessRecipe(NamespacedKey(plugin, "$number"), inv.getItem(15)!!)
                        .addIngredientNotAir(inv.getItem(2)).addIngredientNotAir(inv.getItem(3)).addIngredientNotAir(inv.getItem(4))
                        .addIngredientNotAir(inv.getItem(11)).addIngredientNotAir(inv.getItem(12)).addIngredientNotAir(inv.getItem(13))
                        .addIngredientNotAir(inv.getItem(20)).addIngredientNotAir(inv.getItem(21)).addIngredientNotAir(inv.getItem(22))

                    plugin.server.addRecipe(shapelessRecipe)

                    val type = 4
                    recipeConfig.set("$number.type", type)
                    for (i in 0 until 9) {
                        recipeConfig.set("$number.recipe.$i", inv.getItem(recipeList[i]))
                    }
                    recipeConfig.set("$number.result", inv.getItem(recipeResult))
                    number++

                    recipeConfig.save(recipeListFile)

                    this@openAddRecipeGUIVerShapeless.closeInventory()

                    return@slot
                }
            }

            this@openAddRecipeGUIVerShapeless.sendMessage(
                Component.text("Recipe must not be added by only AIR!")
                    .color(KapeTextColor.RED.toTextColor())
            )
        }
    }
}
