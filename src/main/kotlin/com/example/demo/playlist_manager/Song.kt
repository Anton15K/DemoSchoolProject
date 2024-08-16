package com.example.demo.playlist_manager

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "songs")
class Song : Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, name = "name")
    val songName: String? = null

    @Column(nullable = true, name = "spotifyId", unique = true)
    val spotifyId: String? = null

    @Column(nullable = true, name = "youtubeId", unique = true)
    val youtubeId: String? = null

    @JsonIgnore
    @ManyToMany(mappedBy = "songs")
    val playlists: MutableSet<Playlist> = mutableSetOf()

    @ManyToOne()
    @JoinTable(
        name = "song_album",
        joinColumns = [JoinColumn(name = "song_id")],
        inverseJoinColumns = [JoinColumn(name = "album_id")],

    )
    lateinit var album: Album

    @ManyToOne()
    @JoinTable(
        name = "song_artist",
        joinColumns = [JoinColumn(name = "song_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")]
    )
    lateinit var artist: Artist

}