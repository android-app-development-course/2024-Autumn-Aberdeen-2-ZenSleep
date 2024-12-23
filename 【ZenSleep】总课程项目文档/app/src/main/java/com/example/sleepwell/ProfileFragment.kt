package com.example.sleepwell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var userEmail: TextView
    private lateinit var userName: TextView
    private lateinit var userAvatar: ImageView
    private lateinit var btnLogout: Button
    private lateinit var btnSwitchUser: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        userEmail = view.findViewById(R.id.user_email)
        userName = view.findViewById(R.id.user_name)
        userAvatar = view.findViewById(R.id.user_avatar)
        btnLogout = view.findViewById(R.id.btn_logout)
        btnSwitchUser = view.findViewById(R.id.btn_switch_user)

        // 获取传递的用户信息
        val email = arguments?.getString("email")
        val name = arguments?.getString("name")

        userEmail.text = email
        userName.text = name

        // 为按钮添加点击事件
        btnLogout.setOnClickListener {
            // 处理退出逻辑，返回登录页面
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        btnSwitchUser.setOnClickListener {
            // 处理切换用户逻辑，返回登录页面
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return view
    }
}
