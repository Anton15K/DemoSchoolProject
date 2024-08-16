package com.example.demo.auth

import com.example.demo.playlist_manager.UserRepository
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
class SecutiryConfig (
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
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
            }
            .logout { logout ->
                logout.logoutUrl("/logout")
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
    fun customAuthenticationSuccessHandler(): CustomAuthenticationSuccessHandler {
        return CustomAuthenticationSuccessHandler()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }
}