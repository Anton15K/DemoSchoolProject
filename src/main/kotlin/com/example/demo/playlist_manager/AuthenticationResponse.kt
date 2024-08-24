package com.example.demo.playlist_manager

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
)