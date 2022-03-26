package com.josesilveiraa.kurrency.listener

import com.josesilveiraa.kurrency.Kurrency
import com.josesilveiraa.kurrency.manager.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun on(event: PlayerJoinEvent) {
        val player = event.player
        val uuid: String = player.uniqueId.toString()

        if(!UserManager.exists(uuid)) {
            UserManager.createUser(uuid, 0.0, 0)
        }

        if(!Kurrency.users.containsKey(uuid)) {
            val user = UserManager.getUser(uuid)

            Kurrency.users[uuid] = user!!
        }
    }

}