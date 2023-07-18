package com.example.fragment.MusicPlayerServices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.fragment.R
import com.example.fragment.`object`.Constant


class MusicPlayerService : Service() {
    val CHANNEL_ID = "ForegroundServiceChannel"

    var mediaPlayer:MediaPlayer?=null



    override fun onCreate() {
        super.onCreate()




        }

    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("inputExtra")
        val inputExtraaudiomp3 = intent.getIntExtra("inputExtraaudiomp3",0)
        val inputtitle = intent.getStringExtra("inputExtratitle")


        mediaPlayer = MediaPlayer.create(this,inputExtraaudiomp3 );
//        mediaPlayer?.setLooping(false);
        createNotificationChannel()
        val notificationIntent = Intent(this, MusicPlayerService::class.java)
        notificationIntent.action= Constant.ACTION.STARTFOREGROUND_ACTION
        notificationIntent.action= Constant.ACTION.STOPFOREGROUND_ACTION
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(inputtitle)
            .setContentText(input)
            .setSmallIcon(com.example.fragment.R.drawable.baseline_audio_file_24)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.baseline_pause_circle_outline_24,"Stop",pendingIntent)
            .addAction(R.drawable.baseline_play_circle_24,"play",pendingIntent)
            .build()
        startForeground(1, notification)

        mediaPlayer?.start();
        return START_STICKY;
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}


