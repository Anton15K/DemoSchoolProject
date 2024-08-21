package com.example.demo.api

import com.example.demo.playlist_manager.*
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import org.springframework.stereotype.Service

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.services.youtube.YouTubeScopes
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTube.PlaylistItems
import com.google.api.services.youtube.model.*

@Service
class YoutubeApiService(
    private val userRepository: UserRepository,
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val playlistRepository: PlaylistRepository,
    private val albumRepository: AlbumRepository,
) {
    final val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    final val APPLICATION_NAME: String = "Playlist-Manager"

    fun retrieveAPI(accessToken: String): YouTube {
        val credential = Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken).setExpiresInSeconds(5)
        val api = YouTube.Builder(NetHttpTransport(), JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build()
        return api
    }

    fun easySearch(accessToken: String) {
        val api = retrieveAPI(accessToken)

        val searchResult = api.playlists().list("snippet")
            .setMine(true)
            .execute()

        val itemsList = searchResult.items
        val channel = itemsList[0]
        val title = channel.snippet.title
        println("TITLE $title")
    }
}