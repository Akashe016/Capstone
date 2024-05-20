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

class EditInvoice : AppCompatActivity() {

    private lateinit var clientNameEdit: EditText
    private lateinit var amountEdit: EditText
    private lateinit var dateEdit: EditText
    private lateinit var descriptionEdit: EditText
    private lateinit var saveInvoiceButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.editinvoice_activity)

        clientNameEdit = findViewById(R.id.clientEditText)
        amountEdit = findViewById(R.id.editAmountText)
        dateEdit = findViewById(R.id.editInvoiceDate)
        descriptionEdit = findViewById(R.id.editDescription)
        saveInvoiceButton = findViewById(R.id.saveButton)
        val calendarIcon = findViewById<ImageView>(R.id.editCalender)

        val userId = intent.getLongExtra("USER_ID", -1)
        val clientName = intent.getStringExtra("CLIENT_NAME")
        val amount = intent.getDoubleExtra("AMOUNT",0.0)
        val date = intent.getStringExtra("DATE")
        val description = intent.getStringExtra("DESCRIPTION")

        println(userId.toString())
        clientNameEdit.setText(clientName)
        amountEdit.setText(amount.toString())
        dateEdit.setText(date)
        descriptionEdit.setText(description)

        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }

        saveInvoiceButton.setOnClickListener() {

            if (validateInputs()) {
                val user_id = userId.toLong()
                val clientName = clientNameEdit.text.toString()
                val amount = amountEdit.text.toString().toDouble()
                val date = dateEdit.text.toString()
                val description = descriptionEdit.text.toString()

                updateInvoice(user_id, clientName, amount, date, description)
            }
        }
    }
    private fun validateInputs(): Boolean {
        val clientName = clientNameEdit.text.toString()
        val amount = amountEdit.text.toString()
        val date = dateEdit.text.toString()
        val description = descriptionEdit.text.toString()

        if (clientName.length < 5) {
            clientNameEdit.error = "Client name must be at least 5 characters"
            clientNameEdit.requestFocus()
            return false
        }
        if (amount.isEmpty() || amount.toDouble() < 3000) {
            amountEdit.error = "Amount must be a positive number and minimum INR 3000"
            amountEdit.requestFocus()
            return false
        }

        if (date.isEmpty()) {
            dateEdit.error = "Please select date!"
            dateEdit.requestFocus()
            return false
        }
        if (description.isEmpty()) {
            descriptionEdit.error = "Please fill the description!"
            descriptionEdit.requestFocus()
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

                dateEdit.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    private fun updateInvoice(userId: Long, clientName: String, amount: Double, date: String, description: String) {

        val app: MyApp = application as MyApp

        val accountId: Long = app.getCurrentAccountId()

        app.coroutineScope().launch {

            val user = User(
                id = userId,
                clientName = clientName,
                amount = amount,
                date = date,
                description = description,
                accountid = app.getCurrentAccountId()
            )
            try {
                val response = app.invoiceCurdInterface().editInvoice(user, accountId)

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@EditInvoice, "Update invoice successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@EditInvoice, DashBoardActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@EditInvoice, response.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@EditInvoice, EditInvoice::class.java)
                    startActivity(intent)
                }
            } catch (e: Exception) {

                Toast.makeText(
                    this@EditInvoice, "An error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                Log.e("EditInvoice", "Error : ", e)
            }
        }
    }
}