package com.shawn.ec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcApplication

fun main(args: Array<String>) {
	runApplication<EcApplication>(*args)
}
