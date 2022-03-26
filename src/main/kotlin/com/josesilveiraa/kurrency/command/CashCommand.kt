package com.josesilveiraa.kurrency.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Optional
import co.aikar.commands.annotation.Syntax
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.josesilveiraa.kurrency.Kurrency
import org.bukkit.entity.Player

@CommandAlias("cash|points")
class CashCommand : BaseCommand() {

    @Default
    @Syntax("[player]")
    @CommandCompletion("@players")
    @Description("Shows a player's balance.")
    fun onShow(player: Player, @Optional onlinePlayer: OnlinePlayer?) {
        val target = if(onlinePlayer != null) onlinePlayer.getPlayer() else player
        val targetUuid = target.uniqueId.toString()

        val user = Kurrency.users[targetUuid]

        player.sendMessage("§a${target.name}'s points: §f${user!!.balance}")
    }

}