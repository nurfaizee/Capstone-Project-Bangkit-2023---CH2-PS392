package com.dicoding.bottomnavigationbar.ui.Retrofit

data class UsersResponse(
    val accessToken: String? = null,
    val user: User? = null,
    val refreshToken: String? = null
)

data class User(
    val id: Int? = null,
    val email: String? = null,
    val username: String? = null
)
data class RegisterResponse(
    val success: Boolean,
    val userResponse: UsersResponse? = null,
    val error: Error? = null
)

data class Error(
    val message: String? = null,
    val status: Int? = null
)