package com.josesilveiraa.kurrency.manager

import com.josesilveiraa.kurrency.Kurrency
import com.josesilveiraa.kurrency.dataclass.User
import com.josesilveiraa.kurrency.table.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object SqlManager {

    private val sqlHost = Kurrency.plugin!!.config.getString("mysql.host")
    private val sqlPort = Kurrency.plugin!!.config.getString("mysql.port")
    private val sqlDatabase = Kurrency.plugin!!.config.getString("mysql.database")
    private val sqlUser = Kurrency.plugin!!.config.getString("mysql.user")
    private val sqlPassword = Kurrency.plugin!!.config.getString("mysql.password")

    fun createUser(playerUuid: String, targetNickname: String, targetBalance: Double, targetTransactions: Int) {
        connect()

        transaction {
            Users.insert {
                it[id] = playerUuid
                it[nickname] = targetNickname
                it[balance] = targetBalance
                it[transactions] = targetTransactions
            }
        }
    }

    fun getUser(playerNickname: String): User? {
        connect()

        var user: User? = null

        transaction {
            val query = Users.select { Users.nickname eq playerNickname }.first()

            val id = query[Users.id]
            val nickname = query[Users.nickname]
            val balance = query[Users.balance]
            val transactions = query[Users.transactions]

            user = User(id, nickname, balance, transactions)
        }

        return user
    }

    fun updateUserBalance(playerNickname: String, targetBalance: Double) {
        connect()

        transaction {
            Users.update({ Users.nickname eq playerNickname }) {
                it[balance] = targetBalance
            }
        }
    }

    fun updateUserTransactions(playerNickname: String, targetTransactions: Int) {
        connect()

        transaction {
            Users.update({ Users.nickname eq playerNickname }) {
                it[transactions] = targetTransactions
            }
        }
    }

    fun deleteUser(playerNickname: String) {
        connect()

        transaction {
            Users.deleteWhere { Users.nickname eq playerNickname }
        }
    }

    fun exists(playerNickname: String): Boolean {
        connect()

        var result = false

        transaction {
            val query = Users.select { Users.nickname eq playerNickname }

            result = !query.empty()
        }

        return result
    }

    fun connect() {
        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!, driver = "com.mysql.cj.jdbc.Driver")
    }

}