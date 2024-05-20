package com.example.simpleinvoicegenerationapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch

class DeleteInvoice : AppCompatActivity() {

    private lateinit var clientId: TextView
    private lateinit var clientNameDelete: TextView
    private lateinit var amountDelete: TextView
    private lateinit var dateDelete: TextView
    private lateinit var descriptionDelete: TextView
    private lateinit var deleteInvoiceButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.deleteinvoice_activity)

        clientId = findViewById(R.id.textView)
        clientNameDelete = findViewById(R.id.clientResult)
        amountDelete = findViewById(R.id.amountResult)
        dateDelete = findViewById(R.id.dateResult)
        descriptionDelete = findViewById(R.id.descriptionResult)
        deleteInvoiceButton = findViewById(R.id.button2)

        val userId = intent.getLongExtra("USER_ID",-1)
        val clientName = intent.getStringExtra("CLIENT_NAME")
        val amount = intent.getDoubleExtra("AMOUNT", 0.0)
        val date = intent.getStringExtra("DATE")
        val description = intent.getStringExtra("DESCRIPTION")

        clientNameDelete.text = clientName
        amountDelete.text = amount.toString()
        dateDelete.text = date
        descriptionDelete.text = description

        deleteInvoiceButton.setOnClickListener {

            showDeleteConfirmationDialog(userId.toLong())
        }
    }

    private fun showDeleteConfirmationDialog(userId: Long) {
        AlertDialog.Builder(this)
            .setTitle("Delete Invoice")
            .setMessage("Are you sure you want to delete your invoice?")
            .setPositiveButton("Yes") { _, _ ->
                deleteInvoice(userId)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteInvoice(userId: Long) {

        val app: MyApp = application as MyApp

        app.coroutineScope().launch {
            try {

                val response = app.invoiceCurdInterface().deleteInvoice(userId)
                if (response.isSuccessful) {

                    Toast.makeText(this@DeleteInvoice, "Invoice deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@DeleteInvoice, "Failed to delete invoice",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {

                Toast.makeText(this@DeleteInvoice, "An error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                Log.e("EditInvoice", "Error : ", e)
            }
        }
    }
}
