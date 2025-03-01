package io.github.asr.recipe.commands

import io.github.asr.kape.KapeTextColor
import io.github.asr.kape.text
import io.github.asr.recipe.RecipePlugin
import io.github.asr.recipe.util.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RecipeCommand(private val plugin: RecipePlugin) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        when (command.name) {
            "recipe" -> {
                when (args[0]) {
                    "add" -> sender.openAddRecipeGUI(plugin)

                    "list" -> sender.openRecipeList(plugin, 1)

                    "reload" -> plugin.reloadRecipe()

                    "unload" -> plugin.unloadRecipe()

                    "load" -> plugin.loadRecipe()

                    "autoload" -> {
                        val autoload = recipeConfig.getBoolean("auto-load")
                        if (autoload) plugin.server.sendMessage(text("[Recipe] Auto-Load Disabled")
                            .color(KapeTextColor.RED.toTextColor()))
                        else plugin.server.sendMessage(text("[Recipe] Auto-Load Enabled")
                            .color(KapeTextColor.GREEN.toTextColor()))

                        recipeConfig.set("auto-load", !autoload)
                    }
                }
            }
        }
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        val commandList = mutableListOf<String>()

        if (command.name == "recipe") {
            when (args.size) {
                1 -> {
                    commandList.add("add")
                    commandList.add("list")
                    commandList.add("reload")
                    commandList.add("unload")
                    commandList.add("load")
                    commandList.add("autoload")
                }
            }
        }

        return commandList
    }
}