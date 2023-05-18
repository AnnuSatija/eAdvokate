package com.appsnipp.eadvokate.model

data class InstantServiceCasesModel(
    val message: String?,
    val response: MutableList<InstantServiceResponse>,
    val status: Int?
)

data class InstantServiceResponse(
    val case_assigned_to: Any?,
    val case_complete_date: String,
    val case_start_date: String,
    val customer_id: String,
    val order_date: String,
    val order_id: String,
    val remark: String,
    val service_name: String,
    val status: String,
    val trans_amount: String
)