package io.github.asr.recipe.util

import io.github.asr.kape.KapeTextColor
import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import kotlin.math.min

private const val itemsInPage = 45

private fun Player.openRecipe(plugin: Plugin, recipeNumber: Int) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Recipe")) {
        (0 until 27).forEach { slotNumber ->
            slot(slotNumber, ItemStack(Material.BARRIER).apply {
                editMeta {
                    it.displayName(Component.text(" "))
                }
            })
        }

        /*
        (0 until 9).forEach {
            // TODO : 레시피 보여주기
        }
        */

        slot(recipeResult, recipeConfig.itemStack("$recipeNumber.result")!!)

        slot(26, ItemStack(Material.ARROW).apply {
            editMeta {
                it.displayName(Component.text("뒤로 가기").color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            this@openRecipe.openRecipeList(plugin, (recipeNumber - 1) / itemsInPage + 1)
        }

        // TODO: 2021-10-07 Add Delete Recipe Button.
    }
}

fun Player.openRecipeList(plugin: Plugin, page: Int) {
    this.gui(plugin, InventoryType.CHEST_54, Component.text("Recipe-List")) {
        val start = (page - 1) * itemsInPage
        for (i in start until min(page * itemsInPage, number - start + 1)) {
            slot(i, recipeConfig.itemStack("$i.result")!!) {
                this@openRecipeList.openRecipe(plugin, i)
            }
        }

        (45 until 54).forEach { slotNumber ->
            slot(slotNumber, ItemStack(Material.BARRIER).apply {
                editMeta {
                    it.displayName(Component.text(" "))
                }
            })
        }

        if (page > 1) {
            slot(45, ItemStack(Material.ARROW).apply {
                editMeta {
                    it.displayName(
                        Component.text("뒤 페이지")
                            .color(KapeTextColor.GREEN.toTextColor())
                    )
                }
            }) {
                this@openRecipeList.openRecipeList(plugin, page - 1)
            }
        }

        slot(49, ItemStack(Material.BOOK).apply {
            editMeta {
                it.displayName(Component.text("현재 페이지 $page")
                    .color(KapeTextColor.GREEN.toTextColor()))
            }
        })

        slot(53, ItemStack(Material.ARROW).apply {
            editMeta {
                it.displayName(Component.text("앞 페이지")
                    .color(KapeTextColor.GREEN.toTextColor()))
            }
        }) {
            this@openRecipeList.openRecipeList(plugin, page + 1)
        }

        // TODO: 2021-10-07 Make Recipe List Page Working
    }
}