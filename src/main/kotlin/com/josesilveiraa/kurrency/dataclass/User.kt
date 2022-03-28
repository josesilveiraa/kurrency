package com.josesilveiraa.kurrency.dataclass

import com.josesilveiraa.kurrency.manager.SqlManager

data class User(val uuid: String, val nickname: String, var balance: Double, var transactions: Int) {
    fun save() {
        SqlManager.updateUser(nickname, balance, transactions)
    }
}