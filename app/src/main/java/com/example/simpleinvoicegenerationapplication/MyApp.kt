package com.example.simpleinvoicegenerationapplication

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Retrofit

class MyApp : Application(){

    private lateinit var scope: CoroutineScope
    private lateinit var retrofit: Retrofit
    private lateinit var accountCrud: AccountCurd
    private lateinit var invoiceCrud: InvoiceCurd

    override fun onCreate() {
        super.onCreate()

        scope = CoroutineScope(Job() + Dispatchers.Main)
        retrofit = RetrofitConnection.create()
        accountCrud = retrofit.create(AccountCurd::class.java)
        invoiceCrud = retrofit.create(InvoiceCurd::class.java)
    }

    fun coroutineScope() = scope
    fun getRetrofit() = retrofit
    fun invoiceCurdInterface() = invoiceCrud
    fun accountCurdInterface() = accountCrud

    var number : Long = 0

    fun getCurrentAccountId(): Long {
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("accountId", number)
    }
}