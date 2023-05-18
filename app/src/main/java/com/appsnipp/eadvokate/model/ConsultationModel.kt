package com.appsnipp.eadvokate.model

import androidx.lifecycle.MutableLiveData

data class ConsultationModel(
    val message: String,
    val response: MutableList<ConsulationResponse>,
    val status: Int
)

data class ConsulationResponse(
    val advocate_name: String,
    val consult_language: String,
    val consultancy_mode: String,
    val date_time: String,
    val experience: String,
    val photo: String,
    val service_name: String
)
