package com.example.demo.visual

import com.example.demo.playlist_manager.UserService
import com.example.demo.playlist_manager.UserVisualDto
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeController (
    private val userService: UserService,
) {

    @GetMapping("/home")
    fun home(model: Model, request: HttpServletRequest): String {
        val cookie = request.cookies.find { it.name == "id" }
        val id = cookie?.value?.toLong() ?: throw Exception("wow")
        val user = userService.findById(id)
        model.addAttribute("user", user)
        return "home"
    }
}