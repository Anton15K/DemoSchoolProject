package com.example.demo.api

import com.adamratzman.spotify.models.PlaylistTrack
import com.google.api.services.youtube.model.Video
import kotlinx.serialization.json.Json
import java.io.File

open class Api {


    protected var clientID: String = ""
    protected var clientSecret: String = ""
    protected var redirectUri: String = ""

    open suspend fun loadScopes() {
    }

    open suspend fun getUrl(): String {
        return ""
    }

    open suspend fun newAuth(code: String) {
    }

    open suspend fun loadAuth(accessToken: String, refreshToken: String) {
    }

    open fun getIDfromURL(url: String): String {
        return ""
    }

    open suspend fun loadPlaylistFrom(playlistPlatformID: String, userID: Int) {
    }

    open suspend fun updatePlaylist(playlistID: Int, userID: Int) {
    }

    open suspend fun getSongsFromPlaylist(playlistPlatformID: String, playlistDbID: Int) {
    }

    open suspend fun loadPlaylistTo(playlistID: Int, name: String, isPublic: Boolean) {
    }
    open suspend fun changePlaylistID(playlistID: Int, playlistPlatformID: String) {
    }

    protected suspend fun addSongToPlaylist(song: Any, playlistID: Int, counter: Int) {
        var title = ""
        var artist = ""
        var album = ""
        var spotifyID: String? = null
        var youtubeID: String? = null
        var id: Int? = null

        when (song) {
            is PlaylistTrack -> {
                title = song.track!!.asTrack!!.name
                artist = song.track!!.asTrack!!.artists[0].name!!
                album = song.track!!.asTrack!!.album.name
                spotifyID = song.track!!.asTrack!!.id
                //TODO
//                id = newSuspendedTransaction {
//                    Songs.select { Songs.spotifyid eq spotifyID }.singleOrNull()?.get(Songs.id)
//                }

            }

            is Video -> {
                title = song.snippet.title
                var description: List<String>
                try {
                    description = song.snippet.description.split("\n").filter { it != "" }.drop(1)
                    val titleArtist = description[0].split(" · ")
                    title = titleArtist[0]
                    artist = titleArtist[1]
                    album = description[1]
                } catch (e: Exception) {
                    artist = song.snippet.channelTitle.split(" - ").first()
                }
                youtubeID = song.id
                //TODO
//                id = newSuspendedTransaction {
//                    Songs.select { Songs.youtubeid eq youtubeID }.singleOrNull()?.get(Songs.id)
//                }
            }
        }
        if (id == null) {
            id = addSongToDb(title, spotifyID, youtubeID, album, artist)
        } else {
            when (song) {
                is PlaylistTrack -> {
                    //TODO
//                    id = newSuspendedTransaction {
//                        Songs.select { Songs.spotifyid eq spotifyID }.single()[Songs.id]
//                    }
                }

                is Video -> {
                    //TODO
//                    id = newSuspendedTransaction {
//                        Songs.select { Songs.youtubeid eq youtubeID }.single()[Songs.id]
//                    }
                }
            }
        }
        addSongPlaylistDb(id, playlistID, counter)
    }

    private suspend fun addSongToDb(
        name: String,
        spotifyId: String?,
        youtubeId: String?,
        album: String,
        artist: String
    ): Int {
        //TODO
//        val id = newSuspendedTransaction {
//            Songs.insert {
//                it[this.title] = name
//                it[this.spotifyid] = spotifyId
//                it[this.youtubeid] = youtubeId
//            }[Songs.id]
//            SongAlbums.insert {
//                it[this.song] = Songs.select { Songs.spotifyid eq spotifyId }.single()[Songs.id]
//                it[this.album] = album
//            }
//            SongArtists.insert {
//                it[this.song] = Songs.select { Songs.spotifyid eq spotifyId }.single()[Songs.id]
//                it[this.artist] = artist
//            }
//        }[Songs.id]
//        return id
        return 0
    }

    protected suspend fun addSongPlaylistDb(songId: Int, playlistId: Int, position: Int) {
        //TODO
//        newSuspendedTransaction {
//            PlaylistSongs.insert {
//                it[this.playlist] = playlistId
//                it[this.song] = songId
//                it[this.position] = position
//            }
//        }
    }
    protected suspend fun getSongInfo(playlistID: Int): List<List<String>> {
        //TODO
//        val songInfo = newSuspendedTransaction {
//            PlaylistSongs.join(Songs, JoinType.INNER, additionalConstraint = { PlaylistSongs.song eq Songs.id })
//                .select { PlaylistSongs.playlist eq playlistID }.orderBy(PlaylistSongs.position).map {
//                    listOf(
//                        it[Songs.id].toString(),
//                        it[Songs.title].toString(),
//                        it[Songs.spotifyid]?.toString() ?: "",
//                        SongArtists.select { SongArtists.song eq it[Songs.id] }.single()[SongArtists.artist].toString(),
//                        SongAlbums.select { SongAlbums.song eq it[Songs.id] }.single()[SongAlbums.album].toString()
//                    )
//                }
//        }
//        return songInfo
        return listOf(listOf())
    }
    protected suspend fun removeSongPlaylistDb(playlistId: Int) {
        //TODO
//        newSuspendedTransaction {
//            PlaylistSongs.deleteWhere { PlaylistSongs.playlist eq playlistId }
//        }
    }

    protected suspend fun checkRightsUpdate(playlistId: Int, userId: Int): Boolean {
        //TODO
//        val hasRights = newSuspendedTransaction {
//            Playlists.select { Playlists.id eq playlistId }.single()[Playlists.creator] == userId
//        }
//        return hasRights
        return true
    }

    protected suspend fun checkRightsRead(playlistId: Int, userId: Int): Boolean {
        //TODO
//        val hasRights = newSuspendedTransaction {
//            PlaylistUsers.select { PlaylistUsers.playlist eq playlistId and (PlaylistUsers.user eq userId) }
//                .singleOrNull() != null
//        }
//        return hasRights
        return true
    }
    protected suspend fun giveRights(playlistId: Int, userId: Int) {
        //TODO
//        newSuspendedTransaction {
//            PlaylistUsers.insert {
//                it[playlist] = playlistId
//                it[user] = userId
//            }
//        }
    }

}