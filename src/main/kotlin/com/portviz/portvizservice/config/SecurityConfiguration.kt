package com.portviz.portvizservice.config

import com.portviz.portvizservice.services.security.DefaultUserDetailsService
import com.portviz.portvizservice.utils.SecurityRoles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers(AntPathRequestMatcher.antMatcher("/jakarta.faces.resource/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .formLogin {
                it.loginPage("/login.xhtml")
                    .permitAll()
                    .failureUrl("/login.xhtml?error=true")
                    .successForwardUrl("/index.xhtml")
            }
            .logout {
                it.logoutSuccessUrl("/login.xhtml")
            }
            .csrf {
                it.disable()
            }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return DefaultUserDetailsService()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("${SecurityRoles.ROLE_EDITOR.name} > ${SecurityRoles.ROLE_VIEWER.name}");
        return roleHierarchy;
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}