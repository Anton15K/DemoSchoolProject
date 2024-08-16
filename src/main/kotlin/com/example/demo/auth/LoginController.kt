package com.example.demo.auth

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class LoginController {
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/home")
    fun home(): String {
        return "home"
    }
}