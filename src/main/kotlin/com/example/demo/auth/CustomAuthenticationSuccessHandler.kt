package com.example.demo.auth

import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException


class CustomAuthenticationSuccessHandler : AuthenticationSuccessHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?, response: HttpServletResponse,
        authentication: Authentication?
    ) {



        val cookie: Cookie = Cookie("id", "1")
        cookie.maxAge = 7 * 24 * 60 * 60
        cookie.isHttpOnly = true
        cookie.path = "/"

        // Add the cookie to the response
        response.addCookie(cookie)

        // Redirect to the default page after login
        response.sendRedirect("/")
    }
}