package com.okue.midori.rest.api

import mu.KotlinLogging
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class GreetingController {
    private val log = KotlinLogging.logger {}

    @PostMapping("/{serviceName}/{method}")
    fun hello(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable serviceName: String,
        @PathVariable method: String
    ): String {
        log.info { "---> $request" }
        response.contentType = "application/grpc+json"
        return """
            {
                "message": "aaaaaaaaa"
            }
        """
    }

    data class Rep(val message: String)
}
