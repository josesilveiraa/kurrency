package com.josesilveiraa.kurrency.manager

import com.josesilveiraa.kurrency.dataclass.User
import com.josesilveiraa.kurrency.table.Users
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object UserManager {

    fun createUser(playerUuid: String, targetBalance: Double, targetTransactions: Int) {
        transaction {
            Users.insert {
                it[id] = playerUuid
                it[balance] = targetBalance
                it[transactions] = targetTransactions
            }
        }
    }

    fun getUser(playerUuid: String): User? {

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
        transaction {
            Users.update({ Users.id eq playerUuid }) {
                it[Users.balance] = targetBalance
            }
        }
    }

    fun updateUserTransactions(playerUuid: String, targetTransactions: Int) {
        transaction {
            Users.update({ Users.id eq playerUuid }) {
                it[Users.transactions] = targetTransactions
            }
        }
    }

    fun deleteUser(playerUuid: String) {
        transaction {
            Users.deleteWhere { Users.id eq playerUuid }
        }
    }

    fun exists(playerUuid: String): Boolean {
        var result = false

        transaction {
            val query = Users.select { Users.id eq playerUuid }

            result = !query.empty()
        }

        return result
    }

}