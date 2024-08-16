package com.example.demo.playlist_manager

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable


@Entity
@Table(name = "artists")
class Artist : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, name = "name")
    val name: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    val songs: MutableSet<Song> = mutableSetOf()


    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    val albums: MutableSet<Album> = mutableSetOf()

}