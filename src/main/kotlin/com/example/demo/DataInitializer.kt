package com.example.demo

import com.example.demo.playlist_manager.RoleService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class DataInitializer (
    private val roleService: RoleService
) {

    @Bean
    fun loadData(): CommandLineRunner {
        return CommandLineRunner { _: Array<String?>? ->
            roleService.createRoleIfNotFound("ROLE_USER")
            roleService.createRoleIfNotFound("ROLE_ADMIN")
        }
    }
}