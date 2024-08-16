package com.example.demo.playlist_manager

import java.io.Serializable

/**
 * DTO for {@link com.example.demo.playlist_manager.User}
 */
data class UserVisualDto(
    var username: String? = null,
    var playlists: MutableSet<PlaylistDto> = mutableSetOf(),
    var comments: MutableSet<CommDto> = mutableSetOf()
) : Serializable