package com.josesilveiraa.kurrency.listener

import com.josesilveiraa.kurrency.Kurrency
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener : Listener {

    @EventHandler
    fun on(event: PlayerQuitEvent) {
        val player = event.player
        val user = Kurrency.cache[player.name]

        user!!.save()
    }

}