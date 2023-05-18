package com.appsnipp.eadvokate.model

data class PendingPaymentAcceptModel(
    val message: String?,
    val response: ResponseX,
    val status: Int?
)

data class ResponseX(
    val order_hash: String,
    val order_id: String
)
