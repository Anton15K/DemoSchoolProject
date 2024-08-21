package com.example.demo.api

import kotlinx.serialization.Serializable

@Serializable
data class Secret (
    val clientID: String,
    val clientSecret: String,
    val redirectUri: String
)