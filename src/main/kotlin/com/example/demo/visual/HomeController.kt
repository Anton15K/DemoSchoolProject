package com.example.demo.visual

import com.example.demo.api.YoutubeApiService
import com.example.demo.playlist_manager.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping


@Controller
class HomeController (
    private val userService: UserService,
    private val youtubeApiService: YoutubeApiService
) {

    @PostMapping("/home/easySearch")
    fun makeEasySearch(request: HttpServletRequest): String {
        val cookie = request.cookies.find { it.name == "id" }
        val id = cookie?.value?.toLong() ?: throw Exception("wow")
        val user = userService.findById(id)
        youtubeApiService.easySearch(user?.youtubeAccToken ?: throw Exception("Access token Error"))
        return "redirect:/home"
    }
    @GetMapping("/home")
    fun home(model: Model, request: HttpServletRequest): String {
        val cookie = request.cookies.find { it.name == "id" }
        val id = cookie?.value?.toLong() ?: throw Exception("wow")
        val user = userService.findById(id)
        model.addAttribute("user", user)
        return "home"
    }
}