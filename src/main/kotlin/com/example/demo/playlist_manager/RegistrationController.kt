package com.example.demo.playlist_manager

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
    fun registerUserAccount(@ModelAttribute("user") userDto: UserSigningDto, model: Model): String {
        try {
            userService.save(userDto)
            return "redirect:/signup?success"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            return "signup"
        }
    }
}