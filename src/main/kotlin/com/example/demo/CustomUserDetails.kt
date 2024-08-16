package com.example.demo

import com.example.demo.playlist_manager.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(user: User) : UserDetails {
    private val user: User = user

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return user.getAuthorities()// User roles (e.g., ROLE_USER, ROLE_ADMIN)
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}