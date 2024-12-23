package com.example.sleepwell

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi

class TimerBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private var mediaPlayer: MediaPlayer? = null

        fun stopAlarm() {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.sleepwell.TIMER_FINISHED") {
            Toast.makeText(context, "计时结束，开始震动！", Toast.LENGTH_SHORT).show()
            // 震动提醒
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                val vibrationEffect = VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(vibrationEffect)
            }
            // 播放响铃
            mediaPlayer = MediaPlayer.create(context, R.raw.song1)
            mediaPlayer?.start()
            // 确保 MediaPlayer 资源释放
            mediaPlayer?.setOnCompletionListener { mp ->
                mp.release()
                mediaPlayer = null
            }
        }
    }
}
