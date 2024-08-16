package com.example.demo.playlist_manager

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface CommentRepository : JpaRepository<Comm, Long> {


    @Transactional
    @Modifying
    @Query("delete from Comm c where c.id = ?1")
    override fun deleteById(id: Long)


    @Query("select c from Comm c where c.playlist = ?1")
    fun findByPlaylist(playlist: Playlist): List<Comm>


    @Query("select c from Comm c where c.user = ?1")
    fun findByUser(user: User): List<Comm>
}