package com.example.demo.auth

import com.example.demo.playlist_manager.UserService
import com.example.demo.playlist_manager.UserSigningDto
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class RegistrationController(
    private val userService: UserService
) {

    @GetMapping("/signup")
    fun showSignUpForm(model: Model): String {
        val userDto = UserSigningDto()
        model.addAttribute("user", userDto)
        return "signup" // Points to signup.html in templates directory
    }

    @PostMapping("/signup")
    fun registerUserAccount(@ModelAttribute("user") userDto: UserSigningDto, model: Model, response : HttpServletResponse): String {
        try {
            val user = userService.save(userDto)

//            val welcomeCookie = Cookie("id", user.id.toString())
//            welcomeCookie.maxAge = 7 * 24 * 60 * 60 // 1 week
//            welcomeCookie.isHttpOnly = true
//            welcomeCookie.path = "/"
//
//
//            // Add the cookie to the response
//            response.addCookie(welcomeCookie)

            return "redirect:/home?success"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            return "signup"
        }
    }
}