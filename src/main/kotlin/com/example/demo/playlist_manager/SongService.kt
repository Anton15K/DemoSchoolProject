package com.example.demo.playlist_manager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SongService (
    private val songRepository: SongRepository
) {
    fun getSong(id : Long) : Song? {
        return songRepository.findById(id).orElse(null)
    }
    fun createSong(song: Song, album: Album, artist: Artist) : Song {
        song.artist = artist
        song.album = album
        var createdSong = songRepository.save(song)
        createdSong = getSong(createdSong.id!!)!!
        return createdSong
    }

}