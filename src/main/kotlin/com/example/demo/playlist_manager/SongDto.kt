package com.example.demo.playlist_manager

import java.io.Serializable

/**
 * DTO for {@link com.example.demo.playlist_manager.Song}
 */
data class SongDto(
    var songName: String? = null,
    var spotifyId: String? = null,
    var youtubeId: String? = null,
    var album: AlbumDto? = null,
    var artist: ArtistDto? = null
) : Serializable