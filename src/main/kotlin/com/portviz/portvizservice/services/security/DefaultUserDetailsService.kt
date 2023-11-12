package com.portviz.portvizservice.services.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class DefaultUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username.isNullOrBlank()) {
            return null
        }
        return User(
            username,
            "\$2a\$10\$wyBwwG6H.DWmcL.iO9zJVeFnLOepzFI7RQrjmes3E4rDZOHe/hxoS",
            listOf(SimpleGrantedAuthority("ROLE_EDITOR"))
        )
    }
}