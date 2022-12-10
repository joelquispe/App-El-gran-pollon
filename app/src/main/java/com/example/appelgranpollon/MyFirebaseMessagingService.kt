package com.example.appelgranpollon

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

const val channelId ="notification_channel"
const val channelName = "com.example.appelgranpollon"
class MyFirebaseMessagingService :FirebaseMessagingService(){
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.getNotification() != null){
            Log.d("INFO",message.notification?.body.toString())
            try{
                generateNotification(message.notification!!.title!!,message.notification!!.body!!)
            }catch(t:Throwable){
                Log.d("INFO",t.stackTraceToString())
            }

        }
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String,message: String):RemoteViews{
        val remoteView = RemoteViews("com.example.appelgranpollon",R.layout.notification)
        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.description,message)
        remoteView.setImageViewResource(R.id.notificationIcon,R.drawable.logo)
        return remoteView
    }

    fun generateNotification(title:String,message:String){
        val intent:Intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        //chanel id,channel name
        var builder:NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setPriority(Notification.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_ALL)
        builder = builder.setContent(getRemoteView(title,message))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notifcationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notifcationChannel)
        }
        notificationManager.notify(0,builder.build())
    }
}