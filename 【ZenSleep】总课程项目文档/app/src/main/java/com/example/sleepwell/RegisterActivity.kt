package com.example.sleepwell

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val btnRegister: Button = findViewById(R.id.buttonRegister)

        btnRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidEmail(email) && isValidPassword(password)) {
                // 假设注册成功，将数据传递回LoginActivity
                val intent = Intent().apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("password", password)
                }
                setResult(RESULT_OK, intent)
                finish() // 结束当前活动并返回登录页面
            } else {
                Toast.makeText(this, "请输入有效的电子邮件和密码（密码至少3位）", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 3
    }
}
