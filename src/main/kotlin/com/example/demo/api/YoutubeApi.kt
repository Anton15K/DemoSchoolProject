package com.example.demo.api


import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.services.youtube.YouTubeScopes
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTube.PlaylistItems
import com.google.api.services.youtube.model.*

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

import java.io.File



class YoutubeApi : Api() {
    var api: YouTube? = null
    private var scopes = listOf<String>()
    override suspend fun loadScopes() {
        scopes = YouTubeScopes.all().toList()
    }

    override suspend fun getUrl(): String {
        val authUrl = GoogleAuthorizationCodeRequestUrl(clientID, redirectUri, scopes).build()
        return authUrl
    }

    override suspend fun newAuth(code: String) {
        val tokenRequest = GoogleAuthorizationCodeTokenRequest(
            NetHttpTransport(), JacksonFactory(), clientID, clientSecret, code, redirectUri
        ).execute()
        val credential =
            Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(tokenRequest.accessToken)
                .setExpiresInSeconds(3600L)
        api = YouTube.Builder(NetHttpTransport(), JacksonFactory(), credential).setApplicationName("Playlist Transfer")
            .build()
    }
    //TODO
//    private suspend fun loadAuth(accessToken: String, userID: Int) {
//        val credential = Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken)
//        if (credential.refreshToken()) {
//            newSuspendedTransaction {
//                Users.update({ Users.id eq userID }) {
//                    it[this.youtubeAccToken] = credential.accessToken
//                }
//            }
//            api = YouTube.Builder(NetHttpTransport(), JacksonFactory(), credential)
//                .setApplicationName("Playlist Transfer").build()
//        } else {
//            throw Exception("Failed to refresh token")
//        }
//    }

    override fun getIDfromURL(url: String): String {
        val code = url.split("list=")
        val id = code[1].split("&")
        return id[0]
    }
    //TODO
//
//    override suspend fun loadPlaylistFrom(playlistPlatformID: String, userID: Int) {
//        val playlist = api!!.playlists().list("snippet").setId(playlistPlatformID).execute().items[0]
//        val name = playlist.snippet.title
//        val playlistDbID = newSuspendedTransaction {
//            Playlists.insert {
//                it[this.title] = name
//                it[this.youtubeid] = playlistPlatformID
//                it[this.creator] = userID
//            }[Playlists.id]
//        }
//        giveRights(playlistDbID, userID)
//        getSongsFromPlaylist(playlistPlatformID, playlistDbID)
//    }
//
//    override suspend fun updatePlaylist(playlistID: Int, userID: Int) {
//        removeSongPlaylistDb(playlistID)
//        val playlistPlatformID = newSuspendedTransaction {
//            Playlists.select { Playlists.id eq playlistID }.single()[Playlists.youtubeid]
//        }
//        getSongsFromPlaylist(playlistPlatformID!!, playlistID)
//    }

    override suspend fun getSongsFromPlaylist(playlistPlatformID: String, playlistDbID: Int) {
        var counter = 1
        var pageToken: String? = null
        do {
            val playlistItems = api!!.playlistItems().list("snippet").setPlaylistId(playlistPlatformID).setMaxResults(50L)
                .setPageToken(pageToken).execute()
            val videos = playlistItems.items.mapNotNull {
                try {
                    it.snippet.resourceId.videoId
                } catch (e: Exception) {
                    println("Error getting video id")
                    null
                }
            }
            for (video in videos) {
                val videoInfo = api!!.videos().list("snippet").setId(video).execute().items[0]
                addSongToPlaylist(videoInfo, playlistDbID, counter)
                counter++
            }
            pageToken = playlistItems.nextPageToken
        } while (pageToken != null)
    }
    //TODO
//    override suspend fun loadPlaylistTo(playlistID: Int, name: String, isPublic: Boolean) {
//        val songInfo = getSongInfo(playlistID)
//        val songs = mutableListOf(Video())
//        for (song in songInfo) {
//            val id = song[0].toInt()
//            val title = song[1]
//            val youtubeid = song[2]
//            val artist = song[3]
//            val album = song[4]
//            if (youtubeid != "") {
//                val video = api!!.videos().list("snippet").setId(youtubeid).execute().items[0]
//                songs.add(video)
//            } else {
//                val search = api!!.search().list("snippet").setQ("$title $artist $album").setMaxResults(1L).execute().items[0]
//                val video = api!!.videos().list("snippet").setId(search.id.videoId).execute().items[0]
//                newSuspendedTransaction {
//                    Songs.update({ Songs.id eq id }) {
//                        it[this.youtubeid] = video.id
//                    }
//                }
//                songs.add(video)
//            }
//        }
//        val createdPlaylistID = api!!.playlists().insert("snippet,status", Playlist().setSnippet(PlaylistSnippet().setTitle(name)).setStatus(
//            PlaylistStatus().setPrivacyStatus(if (isPublic) "public" else "private"))).execute().id
//        for (song in songs) {
//            val playlistItem = PlaylistItem()
//            playlistItem.snippet = PlaylistItemSnippet()
//            playlistItem.snippet.playlistId = createdPlaylistID
//            playlistItem.snippet.resourceId = ResourceId()
//            playlistItem.snippet.resourceId.kind = "youtube#video"
//            playlistItem.snippet.resourceId.videoId = song.id
//            api!!.playlistItems().insert("snippet", playlistItem).execute()
//        }
//        changePlaylistID(playlistID, createdPlaylistID)
//
//    }
//    override suspend fun changePlaylistID(playlistID: Int, playlistPlatformID: String) {
//        newSuspendedTransaction {
//            Playlists.update({ Playlists.id eq playlistID }) {
//                it[youtubeid] = playlistPlatformID
//            }
//        }
//    }
}