package com.example.demo.playlist_manager

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {


    @Query("select r from Role r where r.name = ?1")
    fun findByName(name: String): Optional<Role>
}