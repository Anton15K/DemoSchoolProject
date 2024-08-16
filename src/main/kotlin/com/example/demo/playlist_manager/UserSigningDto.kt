package com.example.demo.playlist_manager

import java.io.Serializable

/**
 * DTO for {@link com.example.demo.playlist_manager.User}
 */
data class UserSigningDto(var username: String? = null, var password: String? = null) : Serializable