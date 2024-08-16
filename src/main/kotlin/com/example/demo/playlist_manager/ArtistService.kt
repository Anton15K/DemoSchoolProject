package com.example.demo.playlist_manager

import org.springframework.stereotype.Service

@Service
class ArtistService(
    private val artistRepository: ArtistRepository
) {
    fun getArtist(id : Long) : Artist? {
        return artistRepository.findById(id).orElse(null)
    }

    fun getArtistByName(name : String) : Artist? {
        return artistRepository.findByName(name).orElse(null)
    }

    fun createArtist(artist: Artist): Artist {
        return artistRepository.save(artist)
    }
}