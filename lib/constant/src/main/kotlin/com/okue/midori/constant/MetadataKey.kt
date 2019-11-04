package com.okue.midori.constant

import io.grpc.Metadata

object MetadataKey {
    private fun metadataKeyOf(name: String) = Metadata.Key.of(name, Metadata.ASCII_STRING_MARSHALLER)
    val REQUEST_ID: Metadata.Key<String> = metadataKeyOf("x-request-id")
    val ORIGINAL_PATH: Metadata.Key<String> =
        metadataKeyOf("x-envoy-original-path")
}

fun Metadata?.getOrEmpty(key: Metadata.Key<String>) = this?.get(key) ?: ""
