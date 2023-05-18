package com.appsnipp.eadvokate.model

data class DashboardBannerModel(
    val message: String?,
    val response: MutableList<BannerResponse>?,
    val status: Int?
)

data class BannerResponse(
    val image: String,
    val link_to: String
)