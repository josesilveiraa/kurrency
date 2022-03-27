package com.josesilveiraa.kurrency.api

import com.josesilveiraa.kurrency.dataclass.User
import com.josesilveiraa.kurrency.manager.UserManager
import org.bukkit.Bukkit
import java.util.UUID

class KurrencyAPI {

    fun getUserByNickName(nickname: String): User? {
        return UserManager.getUser(nickname)
    }

    fun getUserByUniqueId(uuid: UUID): User? {
        val nickname = Bukkit.getOfflinePlayer(uuid).name ?: return null

        return UserManager.getUser(nickname)
    }

    fun setBalance(playerNickname: String, amount: Double): Boolean {
        return UserManager.setBalance(playerNickname, amount)
    }

    fun addToBalance(playerNickname: String, amount: Double): Boolean {
        return UserManager.addToBalance(playerNickname, amount)
    }

    fun removeFromBalance(playerNickname: String, amount: Double): Boolean {
        return UserManager.removeFromBalance(playerNickname, amount)
    }

    fun withdraw(payer: String, receiver: String, amount: Double): Boolean {
        return UserManager.withdraw(payer, receiver, amount)
    }
}