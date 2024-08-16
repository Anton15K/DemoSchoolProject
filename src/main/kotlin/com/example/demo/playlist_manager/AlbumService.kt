package com.example.demo.playlist_manager

import org.springframework.stereotype.Service

@Service
class AlbumService(
    private val albumRepository: AlbumRepository
) {
    fun getAlbum(id : Long) : Album? {
        return albumRepository.findById(id).orElse(null)
    }

    fun getAlbumByNameandArtist(name: String, artist: Artist) : Album? {
        return albumRepository.findByNameAndArtist(name, artist).orElse(null)
    }

    fun createAlbum(album: Album, artist: Artist): Album {
        album.artist = artist
        return albumRepository.save(album)
    }
}