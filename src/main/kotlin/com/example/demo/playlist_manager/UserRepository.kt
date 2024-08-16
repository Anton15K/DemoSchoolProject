package com.example.demo.playlist_manager;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("update User u set u.username = :username where u.id = :id")
    fun updateUsernameById(@Param("username") username: String, @Param("id") id: Long): Int


    @Transactional
    @Modifying
    @Query(
        """update User u set u.spotifyToken = :spotifyAccessToken, u.spotifyRefToken = :spotifyRefreshToken
    where u.id = :id"""
    )
    fun updateSpotifyAccessTokenAndSpotifyRefreshTokenById(
        @Param("spotifyAccessToken") spotifyAccessToken: String,
        @Param("spotifyRefreshToken") spotifyRefreshToken: String,
        @Param("id") id: Long
    ): Int


    @Transactional
    @Modifying
    @Query("update User u set u.youtubeAccToken = :youtubeAccessToken where u.id = :id")
    fun updateYoutubeAccessTokenById(
        @Param("youtubeAccessToken") youtubeAccessToken: String,
        @Param("id") id: Long
    ): Int


    @Transactional
    @Modifying
    @Query("update User u set u.playlists = ?1 where u.id = ?2")
    fun updatePlaylistsById(playlists: Playlist, id: Long): Int


    @Transactional
    @Modifying
    @Query("delete from User u where u.id = ?1")
    override fun deleteById(id: Long)


    @Query("select u from User u where u.username = ?1")
    fun findByUsername(username: String): Optional<User>
}