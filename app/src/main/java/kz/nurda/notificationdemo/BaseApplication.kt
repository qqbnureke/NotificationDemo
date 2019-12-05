package kz.nurda.notificationdemo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BaseApplication : Application() {

    companion object {
        const val CHANNEL_1_ID = "channel1"
        const val CHANNEL_2_ID = "channel2"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                getString(R.string.channel_1_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = getString(R.string.channel_1_description)

            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                getString(R.string.channel_2_name),
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = getString(R.string.channel_2_description)

            val notificationManager =
                getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }
}