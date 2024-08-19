package com.example.demo.auth

import com.example.demo.playlist_manager.UserRepository
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTubeScopes
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.io.File

@Serializable
data class Secret (
    val clientID: String,
    val clientSecret: String,
    val redirectUri: String
)
@Service
class YoutubeApiAuthService (
    userRepository: UserRepository,
) {
    val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    val APPLICATION_NAME: String = "Playlist-Manager"


    fun createAuthUrl() : String {
        val json = File("src/main/resources/secrets/youtubeSecret.json").readText()
        val secrets = Json.decodeFromString<Secret>(json)
        val clientID = secrets.clientID
        val clientSecret = secrets.clientSecret
        val redirectUri = secrets.redirectUri
        val scopes = YouTubeScopes.all().toList()
        val url = GoogleAuthorizationCodeRequestUrl(clientID, redirectUri, scopes).build()
        return url
    }

}