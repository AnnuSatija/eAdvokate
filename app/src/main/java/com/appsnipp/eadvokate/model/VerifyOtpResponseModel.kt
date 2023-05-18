package com.appsnipp.eadvokate.model

data class VerifyOtpResponseModel(
    val message: String,
    val response: Response,
    val status: Int
)

data class Response(
    val connect_sid: String,
    val current_session: String,
    val user_info: UserInfo
)

data class UserInfo(
    val contact_number: String,
    val customer_id: String,
    val email: String,
    val name: String
)
