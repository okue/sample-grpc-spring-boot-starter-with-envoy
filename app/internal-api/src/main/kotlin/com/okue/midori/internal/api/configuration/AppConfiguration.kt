package com.okue.midori.internal.api.configuration

import com.okue.midori.interceptor.LoggingInterceptor
import org.lognet.springboot.grpc.GRpcGlobalInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {
    @Bean
    @GRpcGlobalInterceptor
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }
}
