package com.portviz.portvizservice.security

import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {

    fun isDemoAccount(): Boolean {
        return SecurityContextHolder
            .getContext()
            .authentication
            .authorities
            .any {
                SecurityRoles.ROLE_DEMO_VIEWER.name == it.authority
            }
    }
}