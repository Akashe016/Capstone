package com.example.simpleinvoicegenerationapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import java.util.Calendar

class NewInvoice : AppCompatActivity() {

    lateinit var clientNameEditText: EditText
    lateinit var amountEditText: EditText
    lateinit var dateEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var createInvoiceButton: Button

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContentView(R.layout.newinvoice_activity)

        clientNameEditText = findViewById(R.id.clientName)
        amountEditText = findViewById(R.id.newInvoiceAmount)
        dateEditText = findViewById(R.id.newInvoiceDate)
        descriptionEditText = findViewById(R.id.newInvoiceDesc)
        createInvoiceButton = findViewById(R.id.newInvoiceButton)
        val calendarIcon = findViewById<ImageView>(R.id.calendarIcon)

        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }

        createInvoiceButton.setOnClickListener {

            if (validateInputs()) {
                val clientName = clientNameEditText.text.toString()
                val amount = amountEditText.text.toString().toDouble()
                val date = dateEditText.text.toString()
                val description = descriptionEditText.text.toString()

                createInvoice(clientName, amount, date, description)
            }
        }
    }
    fun validateInputs(): Boolean {
        val clientName = clientNameEditText.text.toString()
        val amount = amountEditText.text.toString()
        val date = dateEditText.text.toString()
        val description = descriptionEditText.text.toString()

        if (clientName.length < 5) {
            clientNameEditText.error = "Client name must be at least 5 characters"
            clientNameEditText.requestFocus()
            return false
        }

        if (amount.isEmpty() || amount.toDouble() < 3000) {
            amountEditText.error = "Amount must be a positive number and minimum INR 3000"
            amountEditText.requestFocus()
            return false
        }

        if (date.isEmpty()) {
            dateEditText.error = "Please select date!"
            dateEditText.requestFocus()
            return false
        }

        if (description.isEmpty()) {
            descriptionEditText.error = "Please fill the description!"
            descriptionEditText.requestFocus()
            return false
        }

        return true
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = "$year/$dayOfMonth/${month + 1}"

                dateEditText.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    fun createInvoice(clientName: String, amount: Double, date: String, description: String) {

        val app: MyApp = application as MyApp
        val accountId: Long = app.getCurrentAccountId()
        app.coroutineScope().launch {

            val user = User(
                clientName = clientName,
                amount = amount,
                date = date,
                description = description,
                accountid = accountId
            )

            try {
                val response = app.invoiceCurdInterface().createInvoice(user,accountId)
                if (response.isSuccessful) {

                    Toast.makeText(this@NewInvoice, "Invoice created successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@NewInvoice, DashBoardActivity::class.java)
                    startActivity(intent)

                } else {

                    Toast.makeText(this@NewInvoice,
                        "Failed to create invoice: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@NewInvoice, NewInvoice::class.java)
                    startActivity(intent)
                }
            } catch (e: Exception) {

                Toast.makeText(this@NewInvoice, "An error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                Log.e("NewInvoice", "Error : ", e)
            }
        }
    }
}