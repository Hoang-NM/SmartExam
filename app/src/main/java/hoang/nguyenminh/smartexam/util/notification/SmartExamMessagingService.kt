package hoang.nguyenminh.smartexam.util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.appInstance
import hoang.nguyenminh.smartexam.ui.main.MainActivity
import timber.log.Timber

class SmartExamMessagingService : FirebaseMessagingService() {

    companion object {
        private val CHANNEL_ID = appInstance().getString(R.string.default_notification_channel_id)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w("Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            task.result?.let { token ->
                Timber.d("FCM token = $token")
            }
        })
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("New FCM token = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.default_notification_channel)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelDescription = getString(R.string.default_notification_channel_description)
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(message: RemoteMessage) {
        val intent = packageManager.getLaunchIntentForPackage(packageName) ?: run {
            Timber.e("Failed to get launch intent for package $packageName")
            Intent(
                this,
                MainActivity::class.java
            ).also {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        createNotificationChannel(notificationManager)
        notificationManager.notify(0, notificationBuilder.build())
    }
}