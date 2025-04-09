package com.example.airtrack

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.airtrack.data.AppDatabase
import com.example.airtrack.workers.FlightDataWorker
import java.util.concurrent.TimeUnit

class FlightTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
        scheduleDailyWork()
    }

    private fun scheduleDailyWork() {
        val workRequest = PeriodicWorkRequestBuilder<FlightDataWorker>(
            24, // Repeat interval
            TimeUnit.HOURS
        ).setInitialDelay(1, TimeUnit.MINUTES) // Initial delay
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "FlightDataCollection",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}