package com.example.demo.playlist_manager;

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ArtistRepository : JpaRepository<Artist, Long> {


    fun findByName(name: String): Optional<Artist>
}