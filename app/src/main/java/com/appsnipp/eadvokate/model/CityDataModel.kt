package com.appsnipp.eadvokate.model

data class CityDataModel(
    val message: String,
    val response: List<CityResponse>,
    val status: Int
)
data class CityResponse(
    val district_name: String,
    val id: Int,
    val state_id: String
)



