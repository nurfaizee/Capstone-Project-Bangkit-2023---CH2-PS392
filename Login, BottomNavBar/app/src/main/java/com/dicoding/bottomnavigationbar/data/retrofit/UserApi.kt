package com.dicoding.bottomnavigationbar.data.retrofit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    ): Response<FileResponse>

}