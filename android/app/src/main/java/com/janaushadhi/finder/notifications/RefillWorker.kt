package com.janaushadhi.finder.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.janaushadhi.finder.MainActivity
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.db.AppDatabase
import com.janaushadhi.finder.utils.Constants

class RefillWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val urgentMeds = db.myMedDao().getAllSync().filter { it.isUrgent() }

        if (urgentMeds.isNotEmpty()) {
            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val names = urgentMeds.joinToString(", ") { it.name }
            val notification = NotificationCompat.Builder(applicationContext, Constants.NOTIF_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_pill_notification)
                .setContentTitle("💊 Refill Reminder")
                .setContentText("$names — due soon!")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("${urgentMeds.size} medicine(s) need refilling soon:\n$names"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notifManager = applicationContext
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifManager.notify(Constants.REFILL_NOTIF_ID, notification)
        }
        return Result.success()
    }
}
