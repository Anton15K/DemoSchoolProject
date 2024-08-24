package com.example.demo.auth

import com.example.demo.playlist_manager.UserController
import com.example.demo.playlist_manager.UserRepository
import com.example.demo.playlist_manager.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView


@RestController
class LoginController (
    private val youtubeApiAuthService: YoutubeApiAuthService,
    private val userService: UserService,
    private val userRepository: UserRepository
) {


    @PostMapping("/login/oauth2/code/google")
    fun handleGoogleCallback(@RequestParam("code") code: String, @RequestParam("scope") scope: String, request: HttpServletRequest): ResponseEntity<String> {
        // Log the received code and scope
        val token = youtubeApiAuthService.getAccessToken(code)
        val cookie = request.cookies.find { it.name == "id" }!!
        val user = userService.findById(cookie.value.toLong())!!
        if (user.youtubeAccToken.isBlank()) {
            user.youtubeAccToken = token
            userRepository.save(user)
            return ResponseEntity(token, HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }
}