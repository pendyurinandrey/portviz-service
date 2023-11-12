package com.portviz.portvizservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
@EnableWebSecurity
class PortvizServiceApplication

fun main(args: Array<String>) {
	runApplication<PortvizServiceApplication>(*args)
}
