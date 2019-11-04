package com.okue.midori.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

object ChannelFactory {
    fun create(prop: ChannelProperties): ManagedChannel {
        return ManagedChannelBuilder
            .forAddress(prop.host, prop.port)
            .intercept(LoggingClientInterceptor())
            .usePlaintext()
            .build()
    }

    fun create(host: String, port: Int): ManagedChannel {
        return ManagedChannelBuilder
            .forAddress(host, port)
            .intercept(LoggingClientInterceptor())
            .usePlaintext()
            .build()
    }

    interface ChannelProperties {
        val host: String
        val port: Int
    }
}
