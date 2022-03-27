package com.josesilveiraa.kurrency.manager

import com.josesilveiraa.kurrency.Kurrency
import com.josesilveiraa.kurrency.dataclass.User

object UserManager {

    private fun getUser(playerNickname: String): User? {
        return if (Kurrency.users.containsKey(playerNickname)) {
            Kurrency.users[playerNickname]!!

        } else if (SqlManager.exists(playerNickname)) {
            SqlManager.getUser(playerNickname)

        } else {
            null
        }
    }

    fun getBalance(playerNickname: String): Double? {
        val user = getUser(playerNickname) ?: return null

        return user.balance
    }

    fun addToBalance(playerNickname: String, amount: Double): Boolean {
        val user = getUser(playerNickname) ?: return false

        return updateUserBalance(playerNickname, user.balance + amount)
    }

    fun removeFromBalance(playerNickname: String, amount: Double): Boolean {
        val user = getUser(playerNickname) ?: return false

        val targetAmount = user.balance - amount

        if(targetAmount < 0) return false

        return updateUserBalance(playerNickname, targetAmount)
    }

    fun setBalance(playerNickname: String, amount: Double): Boolean {
        getUser(playerNickname) ?: return false

        if(amount < 0) return false

        return updateUserBalance(playerNickname, amount)
    }

    fun withdraw(payer: String, receiver: String, amount: Double): Boolean {
        val payerUser = getUser(payer) ?: return false

        if(amount > payerUser.balance) return false

        return addToBalance(receiver, amount)
    }

    private fun updateUserBalance(playerNickname: String, amount: Double): Boolean {
        if (Kurrency.users.containsKey(playerNickname)) {
            Kurrency.users[playerNickname]!!.balance = amount
            return true
        } else if (SqlManager.exists(playerNickname)) {
            SqlManager.updateUserBalance(playerNickname, amount)
            return true
        }
        return false
    }
}
