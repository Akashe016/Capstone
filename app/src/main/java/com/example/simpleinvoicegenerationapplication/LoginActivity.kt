package com.example.simpleinvoicegenerationapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var loginUsername : EditText
    lateinit var loginPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)

        loginUsername = findViewById(R.id.loginUserName)
        loginPassword = findViewById(R.id.loginPassword)

        findViewById<Button>(R.id.loginButton).setOnClickListener{

            if (validateForm()) {

                val username = loginUsername.text.toString()
                val password = loginPassword.text.toString()
                checkUserNameAndPassword(username, password)
            }
        }
        findViewById<TextView>(R.id.signup_link).setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun validateForm(): Boolean {

        val username = loginUsername.text.toString()
        val password = loginPassword.text.toString()

            if (username.isEmpty()) {
                loginUsername.error = "Please enter your username!"
                loginUsername.requestFocus()
                return false
            }
            if (password.isEmpty()) {
                loginPassword.error = "Please enter your password!"
                loginPassword.requestFocus()
                return false
            }
        return true
    }

    fun checkUserNameAndPassword(username: String, password: String) {

        val app: MyApp = application as MyApp

        app.coroutineScope().launch {

            try {
            val response = app.accountCurdInterface().login(username,password)
                val message = response["Login Successfully"]

                val success = response.keys.firstOrNull()

                if (message != null) {
                    app.number = message.toLong()
                }

                if(success.equals("Login Successfully")){

                    Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, DashBoardActivity::class.java)
                    startActivity(intent)
                }
                else{

                    Toast.makeText(this@LoginActivity, "Login Failed!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("LoginActivity", "Error : ", e)
            }
        }
    }
}
