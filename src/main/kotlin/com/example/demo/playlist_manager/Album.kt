package com.example.demo.playlist_manager

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "albums")
class Album : Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = true, name = "name")
    val name: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "album")
    val songs: MutableSet<Song> = mutableSetOf()

    @JsonIgnore
    @ManyToOne
    @JoinTable(
        name = "album_artist",
        joinColumns = arrayOf(JoinColumn(name = "album_id", referencedColumnName = "id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "artist_id", referencedColumnName = "id"))
    )
    lateinit var artist: Artist

}