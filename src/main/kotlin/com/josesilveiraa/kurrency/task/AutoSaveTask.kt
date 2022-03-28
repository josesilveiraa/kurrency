package com.josesilveiraa.kurrency.task

import com.josesilveiraa.kurrency.Kurrency

class AutoSaveTask : Runnable {
    override fun run() {
        Kurrency.cache.forEach { it.value.save() }
    }
}