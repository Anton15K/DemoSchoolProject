package com.example.demo.playlist_manager


import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.io.Serializable
import java.util.stream.Collectors


@Entity
@Table(name = "users")
class User : Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false, name = "username", unique = true)
    lateinit var username: String

    @Column(nullable = false, name = "password")
    lateinit var password: String

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    lateinit var roles: MutableList<Role>

    @Column(nullable = true, name = "spotifyAccToken", length = 511)
    lateinit var spotifyToken: String

    @Column(nullable = true, name = "spotifyRefToken", length = 511)
    lateinit var spotifyRefToken: String

    @Column(nullable = true, name = "youtubeAccToken", length = 511)
    lateinit var youtubeAccToken: String

    @ManyToMany()
    @JsonIgnore
    @JoinTable(
        name = "users_playlists",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "playlist_id", referencedColumnName = "id")]
    )
    var playlists: MutableSet<Playlist> = mutableSetOf()

    @OneToMany
    @JsonIgnore
    @JoinTable(
        name = "user_comments",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "comment_id", referencedColumnName = "id")]
    )
    var comments: MutableSet<Comm> = mutableSetOf()

    fun getAuthorities(): Collection<GrantedAuthority?> {
        return roles.stream()
            .map { role: Role -> SimpleGrantedAuthority(role.name) }
            .collect(Collectors.toList())
    }

}
