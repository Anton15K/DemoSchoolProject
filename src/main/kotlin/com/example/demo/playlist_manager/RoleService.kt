package com.example.demo.playlist_manager

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    @Transactional
    fun createRoleIfNotFound(roleName: String?): Role {
        var role: Role? = roleRepository.findByName(roleName!!).getOrNull()
        if (role == null) {
            role = Role()
            role.name = roleName
            roleRepository.save(role)
        }
        return role
    }
}