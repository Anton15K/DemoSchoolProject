package com.example.demo.playlist_manager

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "playlists")
class Playlist : Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, name = "name")
    val name: String? = null

    @Column(nullable = true, name = "spotifyId")
    val spotifyPlaylistId: String? = null

    @Column(nullable = true, name = "youtubeId")
    val youtubePlaylistId: String? = null

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "playlists_songs",
        joinColumns = [(JoinColumn(name = "songs_id", referencedColumnName = "id")),],
        inverseJoinColumns = [(JoinColumn(name = "playlist_id", referencedColumnName = "id"))]
    )
    val songs: MutableList<Song> = mutableListOf()

    @JsonIgnore
    @ManyToMany(mappedBy = "playlists", cascade = [CascadeType.ALL])
    val users: MutableSet<User> = mutableSetOf()

    @JsonIgnore
    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    lateinit var owner: User

    @OneToMany()
    @JsonIgnore
    @JoinTable(
        name = "playlists_comments",
        joinColumns = [(JoinColumn(name = "playlist_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "comment_id", referencedColumnName = "id"))]
    )
    var comments: MutableList<Comm> = mutableListOf()
}
