package com.okue.midori.api.configuration

import com.okue.midori.api.properties.InternalApiClientProperties
import com.okue.midori.grpc.ChannelFactory
import com.okue.midori.grpc.service.BookingInternalCoroutineGrpc
import com.okue.midori.grpc.service.BookingInternalCoroutineGrpc.BookingInternalCoroutineStub
import com.okue.midori.interceptor.LoggingInterceptor
import org.lognet.springboot.grpc.GRpcGlobalInterceptor
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(InternalApiClientProperties::class)
class AppConfiguration {
    @Bean
    fun bookingInternalStub(prop: InternalApiClientProperties): BookingInternalCoroutineStub {
        val channel = ChannelFactory.create(prop)
        return BookingInternalCoroutineGrpc.newStub(channel)
    }

    @Bean
    @GRpcGlobalInterceptor
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }
}
