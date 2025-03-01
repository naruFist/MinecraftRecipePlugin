package io.github.asr.recipe.util

import io.github.asr.kape.KapeTextColor
import io.github.asr.kape.text
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.Plugin
import java.io.File

val recipeList = listOf(2, 3, 4, 11, 12, 13, 20, 21, 22) // Basic 3 X 3

const val recipeResult = 15

val recipeListFile = File("plugins/AddRecipe/config.yml")
val recipeConfig = YamlConfiguration.loadConfiguration(recipeListFile)

var number: Int = 0

fun YamlConfiguration.itemStack(path: String) =
    if (this.isSet(path)) this.getItemStack(path)!! else null


fun ShapedRecipe.setIngredientNotAir(char: Char, stack: ItemStack?) =
    if (stack != null) setIngredient(char, stack) else this

fun ShapelessRecipe.addIngredientNotAir(stack: ItemStack?) =
    if (stack != null) addIngredient(stack) else this


fun Plugin.loadRecipe() {
    for (i in 0 until number) {
        when (recipeConfig.getInt("$i.type")) {
//            1 -> {
//                val recipe = ShapedRecipe(
//                    NamespacedKey(this, "add-recipe"),
//                    recipeConfig.itemStack("$i.result")!!).shape("a")
//                    .setIngredientNotAir('a', recipeConfig.itemStack("$i.recipe.0"))
//
//                server.addRecipe(recipe)
//            }
//
//            2 -> {
//                server.addRecipe(
//                    add2x2recipe(recipeConfig.itemStack("$i.recipe.0"),
//                    recipeConfig.itemStack("$i.recipe.1"),
//                    recipeConfig.itemStack("$i.recipe.2"),
//                    recipeConfig.itemStack("$i.recipe.3"),
//                    recipeConfig.itemStack("$i.result")!!)
//                )
//            }

            3 -> {
                server.addRecipe(
                    add3x3recipe(recipeConfig.itemStack("$i.recipe.0"),
                        recipeConfig.itemStack("$i.recipe.1"),
                        recipeConfig.itemStack("$i.recipe.2"),
                        recipeConfig.itemStack("$i.recipe.3"),
                        recipeConfig.itemStack("$i.recipe.4"),
                        recipeConfig.itemStack("$i.recipe.5"),
                        recipeConfig.itemStack("$i.recipe.6"),
                        recipeConfig.itemStack("$i.recipe.7"),
                        recipeConfig.itemStack("$i.recipe.8"),
                        recipeConfig.itemStack("$i.result")!!, i)
                )

                server.sendMessage(text("Recipe [recipe:$i] Loaded!").color(KapeTextColor.GREEN.toTextColor()))
            }

            4 -> {
                val recipe = ShapelessRecipe(
                    NamespacedKey(this, "$i"),
                    recipeConfig.itemStack("$i.result")!!)
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.0"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.1"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.2"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.3"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.4"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.5"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.6"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.7"))
                    .addIngredientNotAir(recipeConfig.itemStack("$i.recipe.8"))

                server.addRecipe(recipe)

                server.sendMessage(text("Recipe [recipe:$i] Loaded!").color(KapeTextColor.GREEN.toTextColor()))
            }
        }
    }

    server.sendMessage(text("Recipe Loaded Successful!").color(KapeTextColor.GREEN.toTextColor()))
}

fun Plugin.unloadRecipe() {
    (0 until number).forEach {
        this.server.removeRecipe(NamespacedKey(this, "$it"))
    }

    server.sendMessage(text("Recipe Unloaded Successful!").color(KapeTextColor.RED.toTextColor()))
}

fun Plugin.reloadRecipe() {
    unloadRecipe()
    loadRecipe()

    server.sendMessage(text("Recipe Reloaded Successful!").color(KapeTextColor.GREEN.toTextColor()))
}