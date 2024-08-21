package com.example.demo.auth

import com.example.demo.api.Secret
import com.example.demo.playlist_manager.UserRepository
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTubeScopes
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.io.File


@Service
class YoutubeApiAuthService (
    userRepository: UserRepository,
) {
    final val json = File("src/main/resources/secrets/youtubeSecret.json").readText()
    final val secrets = Json.decodeFromString<Secret>(json)
    val clientID = secrets.clientID
    val clientSecret = secrets.clientSecret
    val redirectUri = secrets.redirectUri

    fun createAuthUrl() : String {
        val scopes = YouTubeScopes.all().toList()
        val url = GoogleAuthorizationCodeRequestUrl(clientID, redirectUri, scopes).build()
        return url
    }

    fun getAccessToken(code : String): String {
        val tokenRequest = GoogleAuthorizationCodeTokenRequest(
            NetHttpTransport(), JacksonFactory(), clientID, clientSecret, code, redirectUri
        ).execute()
        return tokenRequest.accessToken
    }

}