package com.example.demo.playlist_manager

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable


@Entity
@Table(name = "comments")
class Comm : Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


    @Column(nullable = false, name = "text")
    var text: String? = null

    @JsonIgnore
    @ManyToOne(optional = false)
    lateinit var user: User

    @JsonIgnore
    @ManyToOne(optional = false)
    lateinit var playlist: Playlist
}