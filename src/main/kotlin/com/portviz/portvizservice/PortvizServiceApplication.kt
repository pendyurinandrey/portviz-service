package com.portviz.portvizservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
class PortvizServiceApplication

fun main(args: Array<String>) {
	runApplication<PortvizServiceApplication>(*args)
}
