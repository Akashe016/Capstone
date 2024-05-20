package com.example.simpleinvoicegenerationapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DashBoardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycleview_dashboard_activity)

        recyclerView = findViewById(R.id.recycleView)
        addButton = findViewById(R.id.addButton)

        addButton.setOnClickListener() {

            val intent = Intent(this, NewInvoice::class.java)
            startActivity(intent)
        }
        loadData()
    }
       override fun onResume() {
            super.onResume()
            loadData()
        }

        private fun loadData() {
            val app: MyApp = application as MyApp
            val accountId: Long = app.getCurrentAccountId()

            app.coroutineScope().launch {
                val apps: List<User>? = app.invoiceCurdInterface().getAllInvoice(accountId).body()

                apps?.let {
                    println(apps)
                    val adapter = DashBoardAdapter(apps)
                    recyclerView.adapter = adapter;
                    recyclerView.layoutManager = LinearLayoutManager(this@DashBoardActivity)
                    recyclerView.addItemDecoration(
                        DividerItemDecoration(this@DashBoardActivity, DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
    }
}