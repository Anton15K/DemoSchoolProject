package com.example.demo.playlist_manager

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

typealias ApplicationUser = com.example.demo.playlist_manager.User

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username).getOrNull()
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        CustomUserDetails(username, password, this.getAuthorities())
}