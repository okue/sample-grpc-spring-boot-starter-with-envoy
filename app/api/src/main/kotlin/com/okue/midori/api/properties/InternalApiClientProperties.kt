package com.okue.midori.api.properties

import com.okue.midori.grpc.ChannelFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("internal.api.client")
class InternalApiClientProperties(
    override val host: String = "0.0.0.0",
    override val port: Int = 6565
) : ChannelFactory.ChannelProperties
