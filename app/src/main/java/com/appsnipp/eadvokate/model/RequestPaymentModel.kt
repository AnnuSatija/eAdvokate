package com.appsnipp.eadvokate.model

data class RequestPaymentModel(
    val message: String,
    val response: MutableList<RequestResponse>,
    val status: Int
)

data class RequestResponse(
    val amount: String,
    val customer_id: String,
    val generated_at: String,
    val id: Int,
    val request_id: Int,
    val service_id: String
)