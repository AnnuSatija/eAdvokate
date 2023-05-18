package com.appsnipp.eadvokate.model

data class ServicePackegesModel(
    val message: String,
    val response: MutableList<ServicePackegesResponse>,
    val status: Int
)
data class ServicePackegesResponse(
    val experience: String,
    val id: Int,
    val package_type: String,
    val price: Int,
    var check:Boolean
)