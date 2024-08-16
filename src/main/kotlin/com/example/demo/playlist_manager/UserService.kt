package com.example.demo.playlist_manager

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun findById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun save(userDto: UserSigningDto): User {
        val user : User = User()
        user.username = userDto.username!!
        user.password = userDto.password!!

        if (userRepository.findByUsername(user.username).isPresent) {
            throw UsernameNotFoundException("Username is already taken")
        }
        user.password = passwordEncoder.encode(user.password)
        val userRole: Role = roleRepository.findByName("ROLE_USER").get()
        user.roles = mutableListOf(userRole)
        return userRepository.save(user)
    }

    fun updateUsername(id: Long, username: String): User {
        println(username)
        if (userRepository.findByUsername(username).isPresent) {
            throw UsernameNotFoundException("User already exists")
        }
        userRepository.updateUsernameById(id = id, username = username)
        val user = findById(id)!!
        return user
    }

    fun updateSpotifyInfo(id: Long, accessToken: String, refreshToken: String): User {
        userRepository.updateSpotifyAccessTokenAndSpotifyRefreshTokenById(accessToken, refreshToken, id)
        val user = findById(id)!!
        return user
    }

    fun addPlaylist(id: Long, playlist: Playlist): User? {
        val user = findById(id)
        if (user != null) {
            user.playlists.add(playlist)
            println("Playlists: ")
            println(user.playlists.map { it.name })
            val resultedUser = userRepository.save(user)
            return resultedUser
        }
        return null
    }
    fun removePlaylist(id: Long, playlist: Playlist): User? {
        val user = findById(id)
        if (user != null) {
            user.playlists.remove(playlist)
            println("Playlists: ")
            println(user.playlists.map { it.name })
            val result = userRepository.save(user)
            return result
        }
        return null
    }

    fun updateYoutubeInfo(id: Long, accessToken: String): User {
        userRepository.updateYoutubeAccessTokenById(accessToken, id)
        val user = findById(id)!!
        return user
    }

    fun delete(id: Long): Boolean {
        val user = findById(id)
        if (user != null) {
            userRepository.deleteById(id)
            return true
        }
        return false
    }
}