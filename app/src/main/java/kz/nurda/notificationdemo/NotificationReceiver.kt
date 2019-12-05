package kz.nurda.notificationdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        remoteInput?.let {
            val replyText = remoteInput.getCharSequence("key_text_reply")
            val answer = Message(replyText!!, null)
            MainActivity.MESSAGES.add(answer)

            MainActivity.messagingNotification(context!!)
        }
    }
}