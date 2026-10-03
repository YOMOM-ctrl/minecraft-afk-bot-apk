package com.example.afkbot

import android.content.Context
import android.util.Log
import kotlinx.coroutines.delay
import kotlin.random.Random

data class AfkConfig(
    val mode: String = "Idle walk",
    val walkInterval: Long = 3000,
    val turnInterval: Long = 5000,
    val jumpInterval: Long = 10000
)

class AfkController(private val context: Context) {
    private var isActive = false
    private var actionLog = mutableListOf<String>()

    suspend fun startAFK(config: AfkConfig) {
        isActive = true
        Log.d("AfkBot", "Starting AFK mode: ${config.mode}")
        logAction("Started AFK mode: ${config.mode}")

        while (isActive) {
            when (config.mode) {
                "Idle walk" -> simulateWalk(config)
                "Mining" -> simulateMining(config)
                "Farming" -> simulateFarming(config)
            }
        }
    }

    fun stopAFK() {
        isActive = false
        Log.d("AfkBot", "Stopped AFK")
        logAction("Stopped AFK")
    }

    private suspend fun simulateWalk(config: AfkConfig) {
        logAction("Walking forward")
        delay(config.walkInterval)
        if (!isActive) return

        logAction("Turning right")
        delay(config.turnInterval)
        if (!isActive) return

        logAction("Walking forward")
        delay(config.walkInterval)
        if (!isActive) return

        logAction("Turning left")
        delay(config.turnInterval)
    }

    private suspend fun simulateMining(config: AfkConfig) {
        logAction("Mining block")
        delay(config.walkInterval)
        if (!isActive) return

        logAction("Moving to next block")
        delay(config.turnInterval)
        if (!isActive) return

        logAction("Mining another block")
        delay(config.walkInterval)
    }

    private suspend fun simulateFarming(config: AfkConfig) {
        logAction("Checking crops")
        delay(config.walkInterval)
        if (!isActive) return

        logAction("Walking to next row")
        delay(config.turnInterval)
        if (!isActive) return

        logAction("Harvesting and replanting")
        delay(config.walkInterval)
    }

    private fun logAction(action: String) {
        val timestamp = System.currentTimeMillis()
        val logEntry = "[$timestamp] $action"
        actionLog.add(logEntry)
        Log.d("AfkBot", logEntry)
        if (actionLog.size > 100) {
            actionLog.removeAt(0)
        }
    }

    fun getActionLog(): List<String> = actionLog.toList()

    fun clearLog() {
        actionLog.clear()
    }
}
