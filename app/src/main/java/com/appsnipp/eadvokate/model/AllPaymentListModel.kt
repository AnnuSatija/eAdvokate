package com.appsnipp.eadvokate.model

data class AllPaymentListModel(
    val message: String?,
    val response: MutableList<AllPaymentResponse>?,
    val status: Int?
)
data class AllPaymentResponse(
    val amount: String?,
    val card_name: String?,
    val customer_id: String?,
    val failure_message: String?,
    val order_date: String?,
    val order_id: String?,
    val order_status: String?,
    val payment_mode: String?,
    val request_id: Int?,
    val service_name: String?,
    val status_code: String?,
    val status_message: String?,
    val system_time: String?,
    val tracking_id: String?,
    val trans_date: String?
)