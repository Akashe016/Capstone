package com.example.simpleinvoicegenerationapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "CutPasteId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.accountUserName)
        emailEditText = findViewById(R.id.accountEmail)
        passwordEditText = findViewById(R.id.accountPassword)

        findViewById<Button>(R.id.signUpButton).setOnClickListener {

            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val date = LocalDate.now().toString()

            if (validateForm(username,email,password)) {

                createAccount(username, email, password, date)
            }
        }

        findViewById<TextView>(R.id.signup_link).setOnClickListener() {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun validateForm(username: String,email: String,password: String): Boolean {

            if (username.isEmpty()) {
                usernameEditText.error = "Please enter username!"
                usernameEditText.requestFocus()
                return false
            }
            if (email.isEmpty()) {
                emailEditText.error = "Please enter your email!"
                emailEditText.requestFocus()
                return false
            }
            if (password.isEmpty()) {
                passwordEditText.error = "Please enter password!"
                passwordEditText.requestFocus()
                return false
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Please enter a correct email address!"
                emailEditText.requestFocus()
                return false
            }
            if(!checkPassword(password)){

                passwordEditText.error = "Please enter correct password! (eg.. One (UpperCase, LowerCase, Symbol and Number))"
                passwordEditText.requestFocus()
                return false
            }
            if (password.length < 8) {
                passwordEditText.error = "password minimum 8 length "
                passwordEditText.requestFocus()
                return false
            }
        return true
    }

    fun checkPassword(password: String): Boolean {

        var upper = 0
        var lower = 0
        var symbol = 0
        var number = 0

        for (char in password) {
            when {
                char in 'A'..'Z' -> upper++
                char in 'a'..'z' -> lower++
                char in '0'..'9' -> number++
                char in '!'..'/' || char in ':'..'@' || char in '['..'`' || char in '{'..'~' -> symbol++
            }
        }
        return upper > 0 && lower > 0 && number > 0 && symbol > 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAccount(username: String, email: String, password: String, date: String) {

        val app: MyApp = application as MyApp

        app.coroutineScope().launch {

            val account = Account(

                userName = username,
                email = email,
                password = password,
                date = date
            )
            try {
                val response = app.accountCurdInterface().accountCreate(account)

                val message = response["message"]

                if(message.equals("Your account create successfully")){

                    Toast.makeText(this@MainActivity,
                        message, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                else{

                    Toast.makeText(this@MainActivity,
                        message,
                        Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            } catch (e: Exception) {

                println(e.message)

                if (e.message == "HTTP 500") {
                    Toast.makeText(this@MainActivity, "Your account already created, Go back to login", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this@MainActivity,
                    "Error : ${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("MainActivity", "Error : ", e)

            }
        }
    }
}
