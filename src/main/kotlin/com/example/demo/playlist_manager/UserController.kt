package com.example.demo.playlist_manager

import jakarta.transaction.Transactional
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*


@RestController
@Transactional
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val playlistService: PlaylistService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long, response: HttpServletResponse): ResponseEntity<User> {
        val user = userService.findById(id)
        if (user != null) {
            return ResponseEntity(user, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    @PutMapping("/{id}")
    fun updateUserName(@PathVariable("id") id: Long, @RequestBody userIn: User): ResponseEntity<User> {
        val user = userService.findById(id)
        if (user != null) {
            val updatedUser = userService.updateUsername(id, userIn.username!!)
            return ResponseEntity(updatedUser, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}/spotifyConnect")
    fun updateSpotifyConnect(@PathVariable("id") id: Long, @RequestBody userIn: User): ResponseEntity<User> {
        val user = userService.findById(id)
        if (user != null) {
            val updatedUser =
                userService.updateSpotifyInfo(id, userIn.spotifyAccToken, userIn.spotifyRefToken)
            return ResponseEntity(updatedUser, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}/youtubeConnect")
    fun updateYoutubeConnect(@PathVariable("id") id: Long, @RequestBody userIn: User): ResponseEntity<User> {
        val user = userService.findById(id)
        if (user != null) {
            val updatedUser = userService.updateYoutubeInfo(id, userIn.youtubeAccToken)
            return ResponseEntity(updatedUser, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}/playlists")
    fun createAssociation(@RequestBody playlist: Playlist, @PathVariable id: Long): ResponseEntity<User> {
        if (playlist.id == null) return ResponseEntity(HttpStatus.NOT_FOUND)
        val user = userService.addPlaylist(id, playlistService.getPlaylist(playlist.id)!!)
        if (user != null) {
            return ResponseEntity(user, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    @DeleteMapping("/{id}/playlists")
    fun deletePlaylist(@RequestBody playlist: Playlist, @PathVariable("id") id: Long): ResponseEntity<User> {
        if (playlist.id == null) return ResponseEntity(HttpStatus.NOT_FOUND)
        val user = userService.removePlaylist(id, playlistService.getPlaylist(playlist.id)!!)
        if (user != null) {
            return ResponseEntity(user, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long): ResponseEntity<User> {
        val user = userService.findById(id)
        if (user != null) {
            for (playlist in user.playlists) {
                if (playlist.owner == user) {
                    playlistService.deletePlaylist(playlist.id!!)
                }
            }
            userService.delete(id)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)

    }
}