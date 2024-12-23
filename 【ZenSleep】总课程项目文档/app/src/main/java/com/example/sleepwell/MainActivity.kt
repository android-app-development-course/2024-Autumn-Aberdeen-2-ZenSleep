package com.example.sleepwell

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var timerBroadcastReceiver: TimerBroadcastReceiver
    private lateinit var userEmail: String
    private lateinit var userName: String

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 从Intent中获取用户信息
        userEmail = intent.getStringExtra("email") ?: "user@example.com"
        userName = intent.getStringExtra("name") ?: "User Name"

        // Check if the SCHEDULE_EXACT_ALARM permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                }
                else -> {
                    // Directly ask for the permission
                    requestPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
                }
            }
        }

        // 启动 SleepWellService
        val serviceIntent = Intent(this, SleepWellService::class.java)
        startForegroundService(serviceIntent)

        // 注册计时广播接收器
        timerBroadcastReceiver = TimerBroadcastReceiver()
        val filter = IntentFilter("com.example.sleepwell.TIMER_FINISHED")
        registerReceiver(timerBroadcastReceiver, filter)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_notes -> {
                    loadFragment(NotesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_timer -> {
                    loadFragment(TimerFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_music -> {
                    loadFragment(MusicFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_nap -> {
                    loadFragment(NapFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment()
                    val bundle = Bundle().apply {
                        putString("email", userEmail)
                        putString("name", userName)
                    }
                    profileFragment.arguments = bundle
                    loadFragment(profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        // 默认选中“听歌”
        bottomNavigationView.selectedItemId = R.id.navigation_music
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timerBroadcastReceiver)
    }
}
