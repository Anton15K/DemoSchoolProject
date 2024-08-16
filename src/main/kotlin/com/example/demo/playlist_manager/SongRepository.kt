package com.example.demo.playlist_manager;

import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<Song, Long> {
}