package com.josesilveiraa.kurrency.listener

import com.josesilveiraa.kurrency.Kurrency
import com.josesilveiraa.kurrency.manager.SqlManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun on(event: PlayerJoinEvent) {
        val player = event.player
        val uuid: String = player.uniqueId.toString()

        if(!SqlManager.exists(player.name)) {
            SqlManager.createUser(uuid, player.name, 0.0, 0)
        }

        if(!Kurrency.users.containsKey(player.name)) {
            val user = SqlManager.getUser(player.name)

            Kurrency.users[player.name] = user!!
        }
    }

}