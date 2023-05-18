package com.appsnipp.eadvokate.model

data class RequestConsultancyDataModel(
    val message: String,
    val response: ConsultancyResponse,
    val status: Int
)
data class ConsultancyResponse(
    val consultancy_mode: String,
    val customer_id: String,
    val customer_name: String,
    val language: String,
    val request_id: Int,
    val request_time: String,
    val service: String
)