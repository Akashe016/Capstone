package com.example.simpleinvoicegenerationapplication

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class Account(

    val id: Long? =null,
    var userName : String,
    var email : String,
    var password : String,
    var date : String
)

interface AccountCurd {

    @GET("/account/signIn")
    suspend fun login(@Query("username") username: String, @Query("password") password: String
    ): Map<String, String>

    @POST("/account/signUp")
    suspend fun accountCreate(@Body account: Account) : Map<String, String>
}