package com.example.sleepwell

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var numberPicker: NumberPicker
    private lateinit var wakeTimeTextView: TextView
    private lateinit var startTimerButton: Button
    private lateinit var countdownTextView: TextView
    private lateinit var cancelTimerButton: Button
    private lateinit var initialLayout: LinearLayout
    private lateinit var countdownLayout: LinearLayout

    private var countDownTimer: CountDownTimer? = null
    private var pendingIntent: PendingIntent? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberPicker = view.findViewById(R.id.number_picker)
        wakeTimeTextView = view.findViewById(R.id.text_wake_time)
        startTimerButton = view.findViewById(R.id.button_start_timer)
        countdownTextView = view.findViewById(R.id.text_countdown)
        cancelTimerButton = view.findViewById(R.id.button_cancel_timer)
        initialLayout = view.findViewById(R.id.initial_layout)
        countdownLayout = view.findViewById(R.id.countdown_layout)

        numberPicker.minValue = 1
        numberPicker.maxValue = 120

        numberPicker.setOnValueChangedListener { _, _, newVal ->
            updateWakeTime(newVal)
        }

        startTimerButton.setOnClickListener {
            val selectedMinutes = numberPicker.value
            startTimer(selectedMinutes)
        }

        cancelTimerButton.setOnClickListener {
            cancelTimer()
        }

        // 初始化时更新唤醒时间
        updateWakeTime(numberPicker.value)
    }

    private fun updateWakeTime(minutes: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, minutes)
        val wakeTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
        wakeTimeTextView.text = "将在 $wakeTime 唤醒你"
    }

    private fun startTimer(minutes: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TimerBroadcastReceiver::class.java).apply {
            action = "com.example.sleepwell.TIMER_FINISHED"
        }
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = System.currentTimeMillis() + minutes * 60 * 1000
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)

        Toast.makeText(context, "计时开始，$minutes 分钟后将唤醒你", Toast.LENGTH_SHORT).show()

        // 切换到倒计时页面
        initialLayout.visibility = View.GONE
        countdownLayout.visibility = View.VISIBLE

        countDownTimer = object : CountDownTimer((minutes * 60 * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesLeft = millisUntilFinished / 1000 / 60
                val secondsLeft = (millisUntilFinished / 1000) % 60
                countdownTextView.text = "剩余时间：${String.format("%02d:%02d", minutesLeft, secondsLeft)}"
            }

            override fun onFinish() {
                countdownTextView.text = "剩余时间：00:00"
            }
        }.start()
    }

    private fun cancelTimer() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        countDownTimer?.cancel()

        Toast.makeText(context, "计时已取消", Toast.LENGTH_SHORT).show()

        // 停止响铃
        TimerBroadcastReceiver.stopAlarm()

        // 切换回初始页面
        initialLayout.visibility = View.VISIBLE
        countdownLayout.visibility = View.GONE
    }
}
