package com.example.demo

import com.example.demo.playlist_manager.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse


@Service
class CustomUserDetailsService (
) : UserDetailsService {
    @Autowired private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository!!.findByUsername(username).getOrElse {  throw UsernameNotFoundException("User not found") }
        return CustomUserDetails(user)
    }
}