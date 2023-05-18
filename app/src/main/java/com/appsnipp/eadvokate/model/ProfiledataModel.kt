package com.appsnipp.eadvokate.model

data class ProfiledataModel(
    val message: String,
    val response: ProfileResponse,
    val status: Int
)
data class ProfileResponse(
    val contact_number: String,
    val customer_id: String,
    val dat_of_reg: String,
    val email: String,
    val name: String
)