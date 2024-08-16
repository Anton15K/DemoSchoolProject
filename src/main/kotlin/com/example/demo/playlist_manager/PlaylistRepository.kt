package com.example.demo.playlist_manager;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface PlaylistRepository : JpaRepository<Playlist, Long> {


    @Transactional
    @Modifying
    @Query("update Playlist p set p.name = :name where p.id = :id")
    fun updateNameById(@Param("name") name: String, @Param("id") id: Long): Int


    @Transactional
    @Modifying
    @Query("update Playlist p set p.users = :users where p.id = :id")
    fun updateUsersById(@Param("users") users: User, @Param("id") id: Long): Int

    @Transactional
    @Modifying
    @Query("delete from Playlist p where p.id = :id")
    fun deletePlaylistsById(@Param("id") id: Long)

}