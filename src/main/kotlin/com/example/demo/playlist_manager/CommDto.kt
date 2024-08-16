package com.example.demo.playlist_manager

import java.io.Serializable

/**
 * DTO for {@link com.example.demo.playlist_manager.Comm}
 */
data class CommDto(var text: String? = null, var user: UserCommentDto? = null) : Serializable