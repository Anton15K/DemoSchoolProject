package com.example.demo.playlist_manager

import org.springframework.stereotype.Service


@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
) {
    fun getPlaylists(): List<Playlist> = playlistRepository.findAll()

    fun getPlaylist(playlistId: Long): Playlist? = playlistRepository.findById(playlistId).orElse(null)

    fun createPlaylist(playlist: Playlist, user: User): Playlist {
        playlist.owner = user
        var resultedPlaylist = playlistRepository.save(playlist)
        resultedPlaylist = getPlaylist(resultedPlaylist.id!!)!!
        return resultedPlaylist
    }

    fun updatePlaylistName(playlistId: Long, name: String): Playlist? {
        playlistRepository.updateNameById(id = playlistId, name = name)
        val playlist = getPlaylist(playlistId)
        return playlist
    }

    fun addSong(playlistId: Long, song: Song): Playlist? {
        val playlist = getPlaylist(playlistId)
        if (playlist != null) {
            playlist.songs.add(song)
            println("Songs: ")
            println(playlist.songs.map { it.songName })
            return playlistRepository.save(playlist)
        }
        return null
    }
    fun removeSong(playlistId: Long, song: Song): Playlist? {
        val playlist = getPlaylist(playlistId)
        if (playlist != null) {
            playlist.songs.remove(song)
            println("Songs: ")
            println(playlist.songs.map { it.songName })
            return playlistRepository.save(playlist)
        }
        return null
    }
    fun deletePlaylist(playlistId: Long): Boolean {
        val playlist = getPlaylist(playlistId)
        if (playlist != null) {
            playlistRepository.deletePlaylistsById(playlistId)
            return true
        }
        return false
    }


}