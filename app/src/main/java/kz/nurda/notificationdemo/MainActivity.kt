package kz.nurda.notificationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kz.nurda.notificationdemo.BaseApplication.Companion.CHANNEL_1_ID
import kz.nurda.notificationdemo.BaseApplication.Companion.CHANNEL_2_ID

class MainActivity : AppCompatActivity() {

    lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManagerCompat = NotificationManagerCompat.from(this)

        btnChannel1.setOnClickListener { sendOnChannel1() }
        btnChannel2.setOnClickListener { sendOnChannel2() }
    }

    private fun sendOnChannel1(){
        val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
            .setContentTitle(etTitle.text.toString())
            .setContentText(etDescription.text.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)
            .build()

        notificationManagerCompat.notify(1, notification)
    }

    private fun sendOnChannel2(){
        val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_looks_two_black_24dp)
            .setContentTitle(etTitle.text.toString())
            .setContentText(etDescription.text.toString())
            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)
            .build()

        notificationManagerCompat.notify(2, notification)
    }
}
