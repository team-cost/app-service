package com.blisspace.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppServiceApplication

fun main(args: Array<String>) {
  runApplication<AppServiceApplication>(*args)
}
