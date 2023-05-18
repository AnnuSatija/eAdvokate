package com.appsnipp.eadvokate.model

data class GetReviewDocumentResponseModel(
    val message: String,
    val response: ReviewDocumentResponse,
    val status: Int
)
data class ReviewDocumentResponse(
    val customer_id: String="",
    val file_attached: String="",
    val id: Int=0,
    val no_of_pages: Int=0,
    val package_type: Int=0,
    val payment_status: String="",
    val request_at: String="",
    val session: String=""
)