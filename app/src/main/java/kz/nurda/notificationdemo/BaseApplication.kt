package kz.nurda.notificationdemo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Build

class BaseApplication : Application() {

    companion object {
        const val GROUP_1_ID = "group1"
        const val GROUP_2_ID = "group2"
        const val CHANNEL_1_ID = "channel1"
        const val CHANNEL_2_ID = "channel2"
        const val CHANNEL_3_ID = "channel3"
        const val CHANNEL_4_ID = "channel4"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            val group1 = NotificationChannelGroup(GROUP_1_ID, "Group 1")
            val group2 = NotificationChannelGroup(GROUP_2_ID, "Group 2")



            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                getString(R.string.channel_1_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = getString(R.string.channel_1_description)
            channel1.group = GROUP_1_ID


            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                getString(R.string.channel_2_name),
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = getString(R.string.channel_2_description)
            channel2.group = GROUP_1_ID

            val channel3 = NotificationChannel(
                CHANNEL_3_ID,
                "Channel 3",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel3.description = getString(R.string.channel_1_description)
            channel3.group = GROUP_2_ID


            val channel4 = NotificationChannel(
                CHANNEL_4_ID,
                "Channel 4",
                NotificationManager.IMPORTANCE_LOW
            )
            channel4.description = getString(R.string.channel_2_description)
            channel4.group = GROUP_2_ID

            val notificationManager =
                getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannelGroup(group1)
            notificationManager.createNotificationChannelGroup(group2)
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
            notificationManager.createNotificationChannel(channel3)
            notificationManager.createNotificationChannel(channel4)
        }
    }
}