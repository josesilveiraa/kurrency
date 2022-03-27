package com.josesilveiraa.kurrency.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Optional
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import com.josesilveiraa.kurrency.helper.ItemBuilder
import com.josesilveiraa.kurrency.manager.UserManager
import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

@CommandAlias("cash|points")
class CashCommand : BaseCommand() {

    @Default
    @Syntax("[player]")
    @CommandCompletion("@players")
    @Description("Shows a player's balance.")
    fun onShow(sender: CommandSender, @Optional target: String) {
        val user = UserManager.getUser(target)

        if(user == null) {
            sender.sendMessage("§cUser not found.")
            return
        }

        val balance = user.balance

        sender.sendMessage("§a${target}'s points: §f${balance}")
    }

    @Subcommand("set")
    @Syntax("[player] [amount]")
    @CommandCompletion("@players")
    @CommandPermission("kurrency.command.set")
    @Description("Sets the target balance.")
    fun onSet(sender: CommandSender, target: String, amount: Double) {
        if(!UserManager.setBalance(target, amount)) {
            sender.sendMessage("§cAn error occurred.")
            return
        }

        sender.sendMessage("§f${target}'s §abalance set to §f$amount§a.")
    }

    @Subcommand("add")
    @Syntax("[player] [amount]")
    @CommandCompletion("@players")
    @CommandPermission("kurrency.command.add")
    @Description("Adds an amount to target's balance.")
    fun onAdd(sender: CommandSender, target: String, amount: Double) {
        if(!UserManager.addToBalance(target, amount)) {
            sender.sendMessage("§cUser not found.")
            return
        }

        sender.sendMessage("§f${target}'s §abalance set to §f$amount§a.")
    }

    @Subcommand("remove")
    @Syntax("[player] [amount]")
    @CommandCompletion("@players")
    @CommandPermission("kurrency.command.remove")
    @Description("Removes an amount of the target's balance.")
    fun onRemove(sender: CommandSender, target: String, amount: Double) {
        if(!UserManager.removeFromBalance(target, amount)) {
            sender.sendMessage("§cUser not found.")
            return
        }

        sender.sendMessage("§aRemoved §f$amount §afrom §f$target§a's balance.")
    }

    @Subcommand("pay")
    @Syntax("[player] [amount]")
    @CommandCompletion("@players")
    @CommandPermission("kurrency.command.pay")
    @Description("Pays another player.")
    fun onPay(sender: Player, target: String, amount: Double) {
        if(!UserManager.withdraw(sender.name, target, amount)) {
            sender.sendMessage("§cAn error occurred.")
            return
        }

        sender.sendMessage("§aPaid §f$amount §ato §f$target§a.")
    }

    @Subcommand("paper")
    @Syntax("[amount]")
    @CommandPermission("kurrency.command.pay")
    @Description("Creates a claimable paper.")
    fun onPaperCreate(player: Player, value: Double) {

        val item = ItemBuilder(Material.PAPER)
            .enchantment(Enchantment.DURABILITY, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .lore("§7Value: §f$value")
            .build()


        val nbti = NBTItem(item)
        nbti.setDouble("value", value)

        player.inventory.addItem(nbti.item)
    }

}