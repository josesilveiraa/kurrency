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

    fun createUser(playerUuid: String, targetBalance: Double, targetTransactions: Int) {

        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)

        transaction {
            Users.insert {
                it[id] = playerUuid
                it[balance] = targetBalance
                it[transactions] = targetTransactions
            }
        }
    }

    fun getUser(playerUuid: String): User? {


        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)

        var user: User? = null

        transaction {
            val query = Users.select { Users.id eq playerUuid }.first()

            val id = query[Users.id]
            val balance = query[Users.balance]
            val transactions = query[Users.transactions]

            user = User(id, balance, transactions)
        }

        return user
    }

    fun updateUserBalance(playerUuid: String, targetBalance: Double) {
        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)

        transaction {
            Users.update({ Users.id eq playerUuid }) {
                it[Users.balance] = targetBalance
            }
        }
    }

    fun updateUserTransactions(playerUuid: String, targetTransactions: Int) {
        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)

        transaction {
            Users.update({ Users.id eq playerUuid }) {
                it[Users.transactions] = targetTransactions
            }
        }
    }

    fun deleteUser(playerUuid: String) {Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)
        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)

        transaction {
            Users.deleteWhere { Users.id eq playerUuid }
        }
    }

    fun exists(playerUuid: String): Boolean {
        Database.connect("jdbc:mysql://$sqlHost:$sqlPort/$sqlDatabase", user = sqlUser!!, password = sqlPassword!!)

        var result = false

        transaction {
            val query = Users.select { Users.id eq playerUuid }

            result = !query.empty()
        }

        return result
    }

}