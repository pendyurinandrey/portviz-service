package com.portviz.portvizservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PortvizServiceApplication

fun main(args: Array<String>) {
	runApplication<PortvizServiceApplication>(*args)
}
