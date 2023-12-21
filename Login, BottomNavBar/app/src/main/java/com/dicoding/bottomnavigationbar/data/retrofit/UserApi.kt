package com.dicoding.bottomnavigationbar.data.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {
    @FormUrlEncoded
    @POST("login?")
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Call<UsersResponse>
    @FormUrlEncoded
    @POST("register?")
    fun register(
        @Field("username") username: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Call<UsersResponse>
    @POST("predict")
    fun deteksiStunting(@Body request: StuntingRequest): Call<StuntingResponse>

}