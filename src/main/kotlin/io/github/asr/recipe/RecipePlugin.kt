package io.github.asr.recipe

import io.github.asr.recipe.commands.RecipeCommand
import io.github.asr.recipe.util.loadRecipe
import io.github.asr.recipe.util.number
import io.github.asr.recipe.util.recipeConfig
import io.github.asr.recipe.util.recipeListFile
import org.bukkit.plugin.java.JavaPlugin

class RecipePlugin : JavaPlugin() {
    override fun onEnable() {
        try {
            if (!recipeListFile.exists()) {
                recipeConfig.save(recipeListFile)
            }
            recipeConfig.load(recipeListFile)
        } catch (localException: Exception) {
            localException.printStackTrace()
        }

        if (recipeConfig.isSet("number")) number = recipeConfig.getInt("number")
        else recipeConfig.set("number", 0)

        if (!recipeConfig.isSet("auto-load")) recipeConfig.set("auto-load", false)
        else if (recipeConfig.getBoolean("auto-load")) loadRecipe()

        server.getPluginCommand("recipe")?.apply {
            setExecutor(RecipeCommand(this@RecipePlugin))
            tabCompleter = RecipeCommand(this@RecipePlugin)
        }

        // loadRecipe()
    }

    override fun onDisable() {
        recipeConfig.set("number", number)
        recipeConfig.save(recipeListFile)

        // unloadRecipe()
    }
}