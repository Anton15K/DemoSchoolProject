package com.example.demo.playlist_manager

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/albums")
class AlbumController(
    private val albumService: AlbumService
) {
    @GetMapping("/{id}")
    fun getAlbum(@PathVariable id: String): ResponseEntity<Album> {
        val album = albumService.getAlbum(id.toLong())
        if (album != null) {
            return ResponseEntity(album, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}