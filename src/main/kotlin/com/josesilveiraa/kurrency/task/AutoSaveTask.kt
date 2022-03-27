package com.josesilveiraa.kurrency.task

import com.josesilveiraa.kurrency.Kurrency

class AutoSaveTask : Runnable {
    override fun run() {
        Kurrency.users.forEach { it.value.save() }
    }
}