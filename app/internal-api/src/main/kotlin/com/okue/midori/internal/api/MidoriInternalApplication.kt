package com.okue.midori.internal.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MidoriInternalApplication

fun main(args: Array<String>) {
    runApplication<MidoriInternalApplication>(*args)
}
