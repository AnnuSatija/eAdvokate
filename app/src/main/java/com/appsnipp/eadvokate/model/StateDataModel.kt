package com.appsnipp.eadvokate.model

data class StateDataModel(
    val message: String,
    val response: List<State>,
    val status: Int
)
data class State(
    val id: Int,
    val state_title: String
)