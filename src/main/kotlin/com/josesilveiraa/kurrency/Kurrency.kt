package com.josesilveiraa.kurrency

import co.aikar.commands.PaperCommandManager
import com.josesilveiraa.kurrency.dataclass.User
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class Kurrency : JavaPlugin() {

    companion object {
        var plugin: JavaPlugin? = null
        var manager: PaperCommandManager? = null
        var users: MutableMap<String, User> = mutableMapOf()
    }

    override fun onEnable() {
        plugin = this

        logger.info("Starting plugin...")
        init()
    }

    override fun onDisable() {

    }

    private fun init() {
        manager = PaperCommandManager(plugin!!)
    }

}