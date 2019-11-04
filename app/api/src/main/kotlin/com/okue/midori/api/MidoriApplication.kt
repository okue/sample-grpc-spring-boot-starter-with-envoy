package com.okue.midori.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MidoriApplication

fun main(args: Array<String>) {
    runApplication<MidoriApplication>(*args)
}
