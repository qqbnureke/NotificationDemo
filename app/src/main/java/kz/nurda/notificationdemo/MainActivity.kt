package kz.nurda.notificationdemo

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
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

        MESSAGES.add(Message("Good Morning", "Jim"))
        MESSAGES.add(Message("Hi", null))
        MESSAGES.add(Message("Helllo", "Jenny"))

        btnChannel1.setOnClickListener { sendOnChannel1() }
        btnChannel2.setOnClickListener { messagingNotification(this) }
    }

    private fun sendOnChannel1() {

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.chamel)

        val notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
            .setContentTitle(etTitle.text.toString())
            .setContentText(etDescription.text.toString())
            .setLargeIcon(largeIcon)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(getString(R.string.long_text))
                    .setBigContentTitle("Big Content Title")
                    .setSummaryText("Summary text")
            )
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
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(largeIcon)
                    .bigLargeIcon(null)
            )
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
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1, 2, 3)
                    .setMediaSession(mediaSesion.sessionToken)
            )
            .setSubText("Sub text")
            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)
            .build()

        notificationManagerCompat.notify(2, notification)
    }

    companion object {
        val MESSAGES: ArrayList<Message> = ArrayList()

        fun messagingNotification(context: Context) {

            val remoteInput = RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer here...")
                .build()

            val replyIntent = Intent(context, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                replyIntent,
                0
            )

            val notificationAction = NotificationCompat.Action.Builder(
                R.drawable.ic_reply_black_24dp,
                "Reply",
                pendingIntent
            ).addRemoteInput(remoteInput).build()

            val messagingStyle = NotificationCompat.MessagingStyle("Me")
            messagingStyle.conversationTitle = "Group Chat"

            for (chatMessage: Message in MESSAGES) {
                val notificationMessage = NotificationCompat.MessagingStyle.Message(
                    chatMessage.text,
                    chatMessage.timestamp,
                    chatMessage.sender
                )

                messagingStyle.addMessage(notificationMessage)
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
                .setStyle(messagingStyle)
                .addAction(notificationAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.BLUE)
//            .setCategory(NotificationCompat.CATEGORY_PROMO)

                .setAutoCancel(true)
                .build()

            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(1, notification)
        }

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
