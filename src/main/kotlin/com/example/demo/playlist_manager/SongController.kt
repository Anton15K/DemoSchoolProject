package com.example.demo.playlist_manager

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/songs")
class SongController(
    private val songService: SongService,
    private val albumService: AlbumService,
    private val artistService: ArtistService
) {
    @GetMapping("/{id}")
    fun getSong(@PathVariable id: Long): ResponseEntity<Song> {
        val song = songService.getSong(id)
        if (song != null) {
            return ResponseEntity(song, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("")
    fun createSong(@RequestBody song: Song): ResponseEntity<Song> {
        try {

            var artist: Artist? = artistService.getArtistByName(song.artist.name!!)
            var album: Album? = null
            if (artist != null) {
                album = albumService.getAlbumByNameandArtist(name = song.album.name!!, artist = artist)
                if (album == null) {
                   album = albumService.createAlbum(song.album, artist)
                }
            } else {
                artist = artistService.createArtist(song.artist)
                album = albumService.createAlbum(song.album, artist)
            }
            val createdSong = songService.createSong(song, album, artistService.getArtist(artist.id!!)!!)
            println("Song info: ${createdSong.album} - ${createdSong.artist}")
            return ResponseEntity(createdSong, HttpStatus.CREATED)
        } catch (e : Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }




    }
}