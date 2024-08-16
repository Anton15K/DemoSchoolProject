package com.example.demo.playlist_manager

import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/playlists")
class PlaylistController(
    private val playlistService: PlaylistService,
    private val userService: UserService,
    private val songService: SongService,
    private val commentService: CommentService
) {

    @GetMapping("/{id}")
    fun getPlaylistById(@PathVariable("id") id: Long): ResponseEntity<Playlist> {
        val playlist = playlistService.getPlaylist(id)
        if (playlist != null) {
            println("[  ${playlist.owner.username}   ]")
            return ResponseEntity(playlist, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("")
    fun addPlaylist(request : HttpServletRequest, @RequestBody playlist: Playlist): ResponseEntity<Playlist> {
        val ownerId = request.cookies.find { it.name == "id" } ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        println("[  ownerId: ${ownerId.value.toLong()}   ]")
        val user = userService.findById(ownerId.value.toLong()) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val createdPlaylist = playlistService.createPlaylist(playlist, user)
        userService.addPlaylist(ownerId.value.toLong(), createdPlaylist)
        return ResponseEntity(createdPlaylist, HttpStatus.CREATED)
    }

    @PostMapping("/{id}/comments")
    fun addComment(request : HttpServletRequest, @PathVariable("id") id: Long, @RequestBody comment: Comm): ResponseEntity<Comm> {
        val userId = request.cookies.find { it.name == "id" } ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)

        val user = userService.findById(userId.value.toLong()) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val playlist = playlistService.getPlaylist(id)
        if (playlist != null) {
            val createdComment = commentService.addComment(comment, user, playlist)
            return ResponseEntity(createdComment, HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

    }

    @PutMapping("/{id}")
    fun updatePlaylistName(@PathVariable("id") id: Long, @RequestBody playlist: Playlist): ResponseEntity<Playlist> {
        val updatedPlaylist = playlistService.updatePlaylistName(id, playlist.name!!)
        return if (updatedPlaylist != null) {
            ResponseEntity(updatedPlaylist, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/songs/{songId}")
    fun addSongs(@PathVariable("id") id: Long, @PathVariable("songId") songId : Long): ResponseEntity<Playlist> {
        val playlist = playlistService.addSong(id, songService.getSong(songId)!!)
        if (playlist != null) {
            return ResponseEntity(playlist, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{id}/songs/{songId}")
    fun deleteSongs(@PathVariable("id") id: Long, @PathVariable("songId") songId: Long): ResponseEntity<Playlist> {
        val playlist = playlistService.removeSong(id, songService.getSong(songId)!!)
        if (playlist != null) {
            return ResponseEntity(playlist, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{id}")
    fun deletePlaylist(@PathVariable("id") id : Long) : ResponseEntity<Playlist> {
        val playlist = playlistService.getPlaylist(id)
        if (playlist != null) {
            for (user in playlist.users) {
                userService.removePlaylist(user.id!!, playlist)
            }
            commentService.deleteCommentsByPlaylist(playlist)
            val isDeleted = playlistService.deletePlaylist(id)
            if (isDeleted) {
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}