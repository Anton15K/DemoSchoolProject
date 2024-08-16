package com.example.demo.auth

import com.example.demo.playlist_manager.UserRepository
import jakarta.servlet.http.Cookie
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain


@Configuration
@EnableWebSecurity
class SecutiryConfig(
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        http
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/signup", "/login", "/resources/**", "/logout").permitAll()
                    .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().hasAnyRole("USER", "ADMIN")
            }
            .formLogin { formLogin ->
                formLogin.loginPage("/login")
                    .successHandler { request, response, authentication ->
                        val userDetails = authentication?.principal as? CustomUserDetails

                        val userId = userDetails?.user?.id

                        val cookie = Cookie("id", userId.toString())
                        cookie.maxAge = 7 * 24 * 60 * 60
                        cookie.isHttpOnly = true
                        cookie.path = "/"

                        response.addCookie(cookie)

                        response.sendRedirect("/home")
                    }
                    .permitAll()
            }
            .logout { logout ->
                logout.logoutUrl("/logout")
                    .deleteCookies("id")
                    .permitAll()
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .httpBasic {}
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return CustomUserDetailsService()
    }
    

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }
}