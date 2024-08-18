package com.example.demo.auth

import com.example.demo.playlist_manager.Playlist
import com.example.demo.playlist_manager.PlaylistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

//TODO
@Component
class PlaylistPermissionEvaluator (
    private val playlistRepository: PlaylistRepository
) : PermissionEvaluator {

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        return checkPermission(authentication, targetDomainObject, permission)
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        return checkPermission(authentication, targetId, permission)
    }

    private fun checkPermission(
        authentication: Authentication?,
        targetId: Any?,
        permission: Any?
    ): Boolean {
        if (authentication == null || targetId == null || permission !is String) {
            return false
        }

        val playlistId = targetId as Long
        val requiredPermission = permission

        val userDetails = authentication.principal as UserDetails

        // Custom logic to check if the user has access to the playlist
        return hasAccessToPlaylist(userDetails, playlistId)
    }

    private fun hasAccessToPlaylist(userDetails: UserDetails, playlistId: Long): Boolean {
        val playlistOpt: Optional<Playlist> = playlistRepository.findById(playlistId)
        if (playlistOpt.isPresent()) {
            val playlist: Playlist = playlistOpt.get()
            return playlist.users.stream()
                .anyMatch { user -> user.username == userDetails.username }
        }
        return false
    }
}