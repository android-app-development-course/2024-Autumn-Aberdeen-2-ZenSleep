
package com.example.sleepwell

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_REGISTER = 1
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnRegister: Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_REGISTER)
        }

        val btnLogin: Button = findViewById(R.id.buttonLogin)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val editTextName:EditText=findViewById(R.id.editTextName)

        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val name=editTextName.text.toString()

            if (isValidEmail(email) && isValidPassword(password)) {
                // 假设验证通过，跳转到MainActivity
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra("email", email)
                    putExtra("name", name) // Replace with actual user name from your user data source
                }
                startActivity(intent)
                finish() // 结束当前活动，防止用户返回登录页面
            } else {
                // 提示用户输入有效的信息
                Toast.makeText(this@LoginActivity, "请输入有效的电子邮件和密码", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            // 注册信息返回后提示用户进行登录
            data?.let {
                val email = it.getStringExtra("email")
                val password = it.getStringExtra("password")
                val name = it.getStringExtra("name")
                val editTextEmail: EditText = findViewById(R.id.editTextEmail)
                val editTextPassword: EditText = findViewById(R.id.editTextPassword)
                val editTextName: EditText = findViewById(R.id.editTextName)
                editTextEmail.setText(email)
                editTextPassword.setText(password)
                editTextName.setText(name)

            }
            Toast.makeText(this, "注册成功，请使用您的信息登录", Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 3
    }
}