package com.appsnipp.eadvokate.model

data class ServicesDataModel(
    val message: String?,
    val response: MutableList<ServicesResponse>?,
    val status: Int?
)

data class ServicesResponse(
    val created_at: String="",
    val created_by: String="",
    val id: Int=0,
    val image: String="",
    val service_name: String="",
    val service_type: Int=0,
    val status: String="",
    val updated_at: String="",
    val updated_by: String=""
)