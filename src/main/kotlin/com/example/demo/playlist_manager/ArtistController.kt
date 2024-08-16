package com.example.demo.playlist_manager

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/artists")
class ArtistController(
    val artistService: ArtistService
) {
    @GetMapping("/{id}")
    fun getArtist(@PathVariable id: String): ResponseEntity<Artist> {
        val artist = artistService.getArtist(id.toLong())
        if (artist != null) {
            return ResponseEntity(artist, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    fun createArtist(@RequestBody artist: Artist): ResponseEntity<Artist> {
        val createdArtist = artistService.createArtist(artist)
        return ResponseEntity(createdArtist, HttpStatus.CREATED)
    }

}

