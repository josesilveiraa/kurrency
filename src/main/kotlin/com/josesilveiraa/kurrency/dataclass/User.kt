package com.josesilveiraa.kurrency.dataclass

data class User(val uuid: String, val nickname: String, var balance: Double, var transactions: Int)