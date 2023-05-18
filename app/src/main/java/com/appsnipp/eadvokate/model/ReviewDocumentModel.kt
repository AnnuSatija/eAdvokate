package com.appsnipp.eadvokate.model

data class ReviewDocumentModel(
    val message: String?,
    val response: MutableList<ReviewDocResponse>?,
    val status: Int?
)

data class ReviewDocResponse(
    val case_complete_date: String,
    val case_complete_doc: String,
    val case_document: String,
    val case_start_date: String,
    val complete_by: String,
    val customer_id: String,
    val order_date: String,
    val order_id: String,
    val remark: String,
    val service_name: String,
    val status: String,
    val trans_amount: String
)