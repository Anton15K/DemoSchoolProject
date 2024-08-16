package com.example.demo.playlist_manager

import jakarta.persistence.*
import java.io.Serializable


@Entity
@Table(name = "roles")
class Role : Serializable {
    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false)
    var name: String? = null

    @ManyToMany(mappedBy = "roles")
    var users: List<User>? = null
}