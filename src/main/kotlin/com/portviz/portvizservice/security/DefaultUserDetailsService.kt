package com.portviz.portvizservice.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class DefaultUserDetailsService : UserDetailsService {

    companion object {
        val accounts = mapOf(
            "admin" to User(
                "admin",
                "\$2a\$10\$wyBwwG6H.DWmcL.iO9zJVeFnLOepzFI7RQrjmes3E4rDZOHe/hxoS",
                listOf(SimpleGrantedAuthority(SecurityRoles.ROLE_EDITOR.name))
            ),
            "viewer" to User(
                "viewer",
                "\$2a\$10\$wyBwwG6H.DWmcL.iO9zJVeFnLOepzFI7RQrjmes3E4rDZOHe/hxoS",
                listOf(SimpleGrantedAuthority(SecurityRoles.ROLE_VIEWER.name))
            ),
            "demo" to User(
                "viewer",
                "\$2a\$10\$zPd/zMQUXIx71dILjmYlMe/EnEHLpMDZlSjOqtOa.vANkuZZRp5r2",
                listOf(SimpleGrantedAuthority(SecurityRoles.ROLE_DEMO_VIEWER.name))
            )
        )
    }

    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username.isNullOrBlank() || !accounts.containsKey(username)) {
            throw UsernameNotFoundException("$username is not found")
        }
        // User is mutable and it's modified by Spring after returning from this method
        return User.withUserDetails(accounts[username]).build()
    }
}