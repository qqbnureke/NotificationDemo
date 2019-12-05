package kz.nurda.notificationdemo

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kz.nurda.notificationdemo.BaseApplication.Companion.CHANNEL_1_ID
import kz.nurda.notificationdemo.BaseApplication.Companion.CHANNEL_2_ID

class MainActivity : AppCompatActivity() {

    lateinit var notificationManagerCompat: NotificationManagerCompat
    lateinit var mediaSesion: MediaSessionCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManagerCompat = NotificationManagerCompat.from(this)
        mediaSesion = MediaSessionCompat(this, "tag")

        btnChannel1.setOnClickListener { sendOnChannel1() }
        btnChannel2.setOnClickListener { mediaNotification() }
    }

    private fun sendOnChannel1() {

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.chamel)

        val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
            .setContentTitle(etTitle.text.toString())
            .setContentText(etDescription.text.toString())
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.long_text))
                .setBigContentTitle("Big Content Title")
                .setSummaryText("Summary text"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(Color.BLUE)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)
            .setContentIntent(getPendingIntent())
            .setAutoCancel(true)
            .addAction(
                R.mipmap.ic_launcher,
                "Toast",
                getActionPendingIntent()
            )
            .build()

        notificationManagerCompat.notify(1, notification)
    }

    private fun sendOnChannel2() {

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.chamel)


        val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_looks_two_black_24dp)
            .setContentTitle(etTitle.text.toString())
            .setContentText(etDescription.text.toString())
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(largeIcon)
                .bigLargeIcon(null))
            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)
            .build()

        notificationManagerCompat.notify(2, notification)
    }

    private fun mediaNotification() {

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.chamel)

        val notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
            .setSmallIcon(R.drawable.ic_looks_two_black_24dp)
            .setContentTitle(etTitle.text.toString())
            .setContentText(etDescription.text.toString())
            .setLargeIcon(largeIcon)
            .addAction(R.drawable.ic_dislike, "Dislike", null)
            .addAction(R.drawable.ic_previous, "Previous", null)
            .addAction(R.drawable.ic_pause_black_24dp, "Pause", null)
            .addAction(R.drawable.ic_skip_next_black_24dp, "Next", null)
            .addAction(R.drawable.ic_like, "Like", null)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1, 2, 3)
                .setMediaSession(mediaSesion.sessionToken)
            )
            .setSubText("Sub text")
            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)
            .build()

        notificationManagerCompat.notify(2, notification)
    }



    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)

        return PendingIntent.getActivity(
            this,
            0,
            intent,
            0
        )
    }

    private fun getActionPendingIntent(): PendingIntent {
        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra(getString(R.string.toast_message), etDescription.text.toString())

        return PendingIntent.getBroadcast(
            this,
            0,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
