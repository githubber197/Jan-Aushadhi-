package com.janaushadhi.finder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import com.janaushadhi.finder.data.db.AppDatabase
import com.janaushadhi.finder.data.repository.MedicineRepository
import com.janaushadhi.finder.notifications.RefillWorker
import com.janaushadhi.finder.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import timber.log.Timber
import java.util.concurrent.TimeUnit

class JanAushadhiApp : Application() {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: MedicineRepository by lazy { MedicineRepository(database) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // OSMDroid configuration — uses standard SharedPreferences
        val prefs = getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        Configuration.getInstance().load(this, prefs)
        Configuration.getInstance().userAgentValue = packageName

        createNotificationChannel()

        appScope.launch {
            try {
                scheduleRefillWorker()
            } catch (e: Exception) {
                Timber.e(e, "WorkManager init failed")
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIF_CHANNEL_ID,
                Constants.NOTIF_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Reminders to refill your medicines" }
            getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    private fun scheduleRefillWorker() {
        val request = PeriodicWorkRequestBuilder<RefillWorker>(1, TimeUnit.DAYS)
            .addTag(Constants.REFILL_WORK_TAG)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constants.REFILL_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
