package com.portviz.portvizservice.config

import com.portviz.portvizservice.services.security.DefaultUserDetailsService
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
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/jakarta.faces.resource/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .formLogin { login ->
                login
                    .loginPage("/login.xhtml")
                    .permitAll()
                    .failureUrl("/login.xhtml?error=true")
                    .successForwardUrl("/index.xhtml")
            }
            .logout { customizer ->
                customizer.logoutSuccessUrl("/login.xhtml")
            }
            .csrf { customizer ->
                customizer.disable()
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
        roleHierarchy.setHierarchy("ROLE_EDITOR > ROLE_VIEWER");
        return roleHierarchy;
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}