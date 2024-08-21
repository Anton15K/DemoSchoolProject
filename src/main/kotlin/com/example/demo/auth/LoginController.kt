package com.example.demo.auth

import com.example.demo.playlist_manager.UserController
import com.example.demo.playlist_manager.UserRepository
import com.example.demo.playlist_manager.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView


@Controller
class LoginController (
    private val youtubeApiAuthService: YoutubeApiAuthService,
    private val userService: UserService,
    private val userRepository: UserRepository
) {
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/authenticateYoutube")
    fun authenticate(model: Model): String {
        val url = youtubeApiAuthService.createAuthUrl()
        return "redirect:$url"

    }

    @GetMapping("/authenticateYoutube")
    fun authenticate(): String {
        return "authenticateYoutube"
    }

    @GetMapping("/login/oauth2/code/google")
    fun handleGoogleCallback(@RequestParam("code") code: String, @RequestParam("scope") scope: String, request: HttpServletRequest): String {
        // Log the received code and scope
        val token = youtubeApiAuthService.getAccessToken(code)
        println("Authorization Code: $token")
        println("Scope: $scope")
        val cookie = request.cookies.find { it.name == "id" }!!
        val user = userService.findById(cookie.value.toLong())!!
        if (user.youtubeAccToken.isNullOrBlank()) {
            user.youtubeAccToken = token
            println("Token ${user.youtubeAccToken}")
            userRepository.save(user)
        }
        // Handle the code: exchange it for an access token, etc.
        // For example, you might call a service method here to handle the token exchange.

        // Redirect or return a view
        return "redirect:/home"
    }
}