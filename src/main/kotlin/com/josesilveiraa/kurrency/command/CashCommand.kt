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
import com.josesilveiraa.kurrency.Kurrency
import com.josesilveiraa.kurrency.manager.SqlManager
import org.bukkit.command.CommandSender

@CommandAlias("cash|points")
class CashCommand : BaseCommand() {

    @Default
    @Syntax("[player]")
    @CommandCompletion("@players")
    @Description("Shows a player's balance.")
    fun onShow(player: CommandSender, @Optional target: String) {
        if(Kurrency.users.containsKey(target)) {
            player.sendMessage("§cError: player not found.")
        }

        val user = Kurrency.users[player.name]

        player.sendMessage("§a${target}'s points: §f${user!!.balance}")
    }

    @Subcommand("set")
    @Syntax("[player] [amount]")
    @CommandCompletion("@players")
    @CommandPermission("kurrency.command.set")
    @Description("Sets the target balance.")
    fun onSet(sender: CommandSender, target: String, amount: Double) {

        if(Kurrency.users.containsKey(target)) {
            Kurrency.users[target]!!.balance = amount
        } else {
            SqlManager.updateUserBalance(target, amount)
        }

        sender.sendMessage("§f${target}'s §abalance set to §f$amount§a.")
    }

    @Subcommand("add")
    @Syntax("[player] [amount]")
    @CommandCompletion("@players")
    @CommandPermission("kurrency.command.add")
    @Description("Adds an amount to target's balance.")
    fun onAdd(sender: CommandSender, target: String, amount: Double) {

        if(Kurrency.users.containsKey(target)) {
            Kurrency.users[target]!!.balance += amount
        } else {
            sender.sendMessage("§cError: player not found.")
        }

        sender.sendMessage("§f${target}'s §abalance set to §f$amount§a.")
    }

}