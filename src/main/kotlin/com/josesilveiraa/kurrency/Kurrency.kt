package com.josesilveiraa.kurrency

import co.aikar.commands.PaperCommandManager
import com.josesilveiraa.kurrency.command.CashCommand
import com.josesilveiraa.kurrency.dataclass.User
import com.josesilveiraa.kurrency.listener.PlayerJoinListener
import com.josesilveiraa.kurrency.listener.PlayerQuitListener
import com.josesilveiraa.kurrency.manager.SqlManager
import com.josesilveiraa.kurrency.table.Users
import com.josesilveiraa.kurrency.task.AutoSaveTask
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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
        HandlerList.unregisterAll(plugin!!)
        users.clear()
    }

    private fun init() {
        manager = PaperCommandManager(plugin!!)
        manager!!.registerCommand(CashCommand())

        saveDefaultConfig()
        createTables()
        registerListeners()
        initTasks()
    }

    private fun createTables() {
        SqlManager.connect()

        transaction {
            SchemaUtils.create(Users)
        }
    }

    private fun registerListeners() {
        server.pluginManager.registerEvents(PlayerJoinListener(), plugin!!)
        server.pluginManager.registerEvents(PlayerQuitListener(), plugin!!)
    }

    private fun initTasks() {
        server.scheduler.runTaskTimerAsynchronously(plugin!!, AutoSaveTask(), 20 * 60 * 5, 20 * 60 * 5)
    }

}