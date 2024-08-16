package com.example.demo.playlist_manager

import java.io.Serializable

/**
 * DTO for {@link com.example.demo.playlist_manager.Playlist}
 */
data class PlaylistDto(var name: String? = null, var songs: MutableList<SongDto> = mutableListOf()) : Serializable