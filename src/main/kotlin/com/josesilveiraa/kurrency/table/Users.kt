package com.josesilveiraa.kurrency.table

import org.jetbrains.exposed.sql.Table


object Users : Table() {
    val id = varchar("uuid", 36)
    val balance = double("balance")
    val transactions = integer("transactions")
}