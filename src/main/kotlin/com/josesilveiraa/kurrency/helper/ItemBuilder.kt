package com.josesilveiraa.kurrency.helper

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


class ItemBuilder(material: Material, amount: Int = 1) {
    private val stack: ItemStack = ItemStack(material, amount)
    private val meta: ItemMeta = stack.itemMeta


    fun name(name: String): ItemBuilder {
        meta.displayName(Component.text(name))
        return this
    }

    fun lore(lore: List<Component>): ItemBuilder {
        meta.lore(lore)
        return this
    }

    fun lore(line: String): ItemBuilder {
        meta.lore()?.add(Component.text(line))
        return this
    }

    fun amount(amount: Int): ItemBuilder {
        stack.amount = amount
        return this
    }

    fun flags(vararg flags: ItemFlag): ItemBuilder {
        stack.addItemFlags(*flags)
        return this
    }

    fun enchantment(enchantment: Enchantment, level: Int): ItemBuilder {
        stack.addUnsafeEnchantment(enchantment, level)
        return this
    }

    fun build(): ItemStack {
        stack.itemMeta = meta
        return stack
    }

}