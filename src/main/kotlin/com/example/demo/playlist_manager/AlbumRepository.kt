package com.example.demo.playlist_manager;

import org.springframework.data.geo.GeoResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AlbumRepository : JpaRepository<Album, Long> {



    @Query("select a from Album a where a.name = ?1")
    fun getAlbumbyName(name: String): GeoResult<Album>


    fun findByName(name: String): Optional<Album>


    fun findByNameAndArtist(name: String, artist: Artist): Optional<Album>
}