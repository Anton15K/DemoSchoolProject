package com.example.demo.api

import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.getSpotifyAuthorizationUrl
import com.adamratzman.spotify.SpotifyCredentials
import com.adamratzman.spotify.SpotifyUserAuthorization
import com.adamratzman.spotify.SpotifyApiOptions
import com.adamratzman.spotify.SpotifyClientApiBuilder
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.models.*
import com.adamratzman.spotify.spotifyClientApi
import com.adamratzman.spotify.utils.Market



class SpotifyApi : Api() {

    private var options = SpotifyApiOptions()
    private var api: SpotifyClientApi? = null

    private val scopes = listOf(
        SpotifyScope.PlaylistModifyPrivate,
        SpotifyScope.PlaylistModifyPublic,
        SpotifyScope.PlaylistReadPrivate,
        SpotifyScope.PlaylistReadCollaborative,
        SpotifyScope.UserLibraryRead,
        SpotifyScope.UserLibraryModify,
        SpotifyScope.UserReadPrivate
    )

    override suspend fun loadScopes() {
        options.requiredScopes = scopes
        options.automaticRefresh = true
        options.testTokenValidity = true
        options.defaultLimit = 1000
    }

    override suspend fun getUrl(): String {
        return getSpotifyAuthorizationUrl(
            scopes = scopes.toTypedArray(),
            clientId = clientID,
            redirectUri = redirectUri
        )
    }

    override suspend fun newAuth(code: String) {
        val creds = SpotifyCredentials()
        creds.clientId = clientID
        creds.clientSecret = clientSecret
        creds.redirectUri = redirectUri
        val auth = SpotifyUserAuthorization(authorizationCode = code)
        val builder = SpotifyClientApiBuilder(credentials = creds, authorization = auth, options = options)
        api = builder.build()
    }

    override suspend fun loadAuth(accessToken: String, refreshToken: String) {
        val token =
            Token(accessToken = accessToken, refreshToken = refreshToken, expiresIn = 3600, tokenType = "Bearer")
        api = spotifyClientApi(clientID, clientSecret, redirectUri, token).build()
        if (!api!!.isTokenValid().isValid) {
            api!!.refreshToken()
        }
    }

    override fun getIDfromURL(url: String): String {
        val playlistID = url.split("/").last().split("?").first()
        return playlistID
    }
    //TODO

//    override suspend fun loadPlaylistFrom(playlistPlatformID: String, userID: Int) {
//        val playlist = api!!.playlists.getPlaylist(playlistPlatformID) ?: return
//        val playlistName = playlist.name
//        val playlistDbID = newSuspendedTransaction {
//            Playlists.insert {
//                it[this.spotifyid] = playlistPlatformID
//                it[title] = playlistName
//                it[creator] = userID
//            } get Playlists.id
//        }
//        giveRights(playlistDbID, userID)
//        getSongsFromPlaylist(playlistPlatformID, playlistDbID)
//    }
//
//    override suspend fun updatePlaylist(playlistID: Int, userID: Int) {
//        removeSongPlaylistDb(playlistID)
//        val playlistSpotifyID = newSuspendedTransaction {
//            Playlists.select { Playlists.id eq playlistID }.single()[Playlists.spotifyid]
//        }
//        getSongsFromPlaylist(playlistSpotifyID!!, playlistID)
//    }
//
//    override suspend fun getSongsFromPlaylist(playlistPlatformID: String, playlistDbID: Int) {
//        var counter = 1
//        var songs = api!!.playlists.getPlaylistTracks(playlistPlatformID, 50, 0, Market.FROM_TOKEN)
//        while (songs.items.isNotEmpty()) {
//            newSuspendedTransaction {
//                for (song in songs.items) {
//                    addSongToPlaylist(song, playlistDbID, counter)
//                    counter++
//                }
//            }
//            songs = api!!.playlists.getPlaylistTracks(playlistPlatformID, 50, counter - 1, Market.FROM_TOKEN)
//        }
//    }
//
//    override suspend fun loadPlaylistTo(playlistID: Int, name: String, isPublic: Boolean) {
//
//        val songInfo = getSongInfo(playlistID)
//        val createdPlaylist = api!!.playlists.createClientPlaylist(name = name, public = isPublic)
//        for (song in songInfo) {
//            val id = song[0].toInt()
//            val title = song[1]
//            val spotifyid = song[2]
//            val artist = song[3]
//            val album = song[4]
//            if (spotifyid != "") {
//                val track = api!!.tracks.getTrack(spotifyid)!!.uri
//                api!!.playlists.addPlayableToClientPlaylist(createdPlaylist.id, track)
//            } else {
//                val track = api!!.search.searchTrack(
//                    "$title $artist $album",
//                    offset = 0,
//                    limit = 1,
//                    market = Market.FROM_TOKEN
//                ).items.firstOrNull()
//                if (track != null) {
//                    api!!.playlists.addPlayableToClientPlaylist(createdPlaylist.id, track.uri)
//                    newSuspendedTransaction {
//                        Songs.update({ Songs.id eq id }) {
//                            it[Songs.spotifyid] = track.id
//                        }
//                    }
//                }
//            }
//        }
//        changePlaylistID(playlistID, createdPlaylist.id)
//    }
//
//    override suspend fun changePlaylistID(playlistID: Int, playlistPlatformID: String) {
//        newSuspendedTransaction {
//            Playlists.update({ Playlists.id eq playlistID }) {
//                it[spotifyid] = playlistPlatformID
//            }
//        }
//    }

}