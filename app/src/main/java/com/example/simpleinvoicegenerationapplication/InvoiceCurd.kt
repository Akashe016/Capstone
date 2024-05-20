package com.example.simpleinvoicegenerationapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

data class User(

    var id: Long? = null,
    val clientName: String,
    var amount: Double,
    var date: String,
    var description: String,
    val accountid : Long

)

interface InvoiceCurd {

    @GET("/invoice/listOfInvoice")
    suspend fun getAllInvoice(@Query("accountId") accountId: Long): Response<List<User>>

    @POST("/invoice/newInvoice")
    suspend fun createInvoice(@Body user: User, @Query("accountId") accountId: Long): Response<ResponseBody>

    @PUT("/invoice/editInvoice")
    suspend fun editInvoice(@Body user: User, @Query("accountId") accountId: Long): Response<ResponseBody>

    @DELETE("/invoice/deleteInvoice")
    suspend fun deleteInvoice(@Query("id") id: Long): Response<ResponseBody>

}