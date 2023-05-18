package com.appsnipp.eadvokate.model

data class PaymentBreakUpModel(
    val message: String,
    val response: PaymentResponse,
    val status: Int
)

data class PaymentResponse(
    val gst: String,
    val price_before_gst: String,
    val price_per_page: String,
    val total: String,
    val order_id:String,
    val order_hash:String,
    val total_pages: Int
)

