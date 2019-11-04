package com.okue.midori.interceptor

import com.okue.midori.constant.ContextKey
import com.okue.midori.constant.MdcKey
import com.okue.midori.constant.MetadataKey
import com.okue.midori.constant.getOrEmpty
import io.grpc.Context
import io.grpc.Contexts
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor

class LoggingInterceptor : ServerInterceptor {
    override fun <ReqT, RespT> interceptCall(
        call: ServerCall<ReqT, RespT>?,
        headers: Metadata?,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        val mdcMap = mapOf(
            MdcKey.ORIGINAL_PATH to headers.getOrEmpty(MetadataKey.ORIGINAL_PATH),
            MdcKey.REQUEST_ID to headers.getOrEmpty(MetadataKey.REQUEST_ID)
        )
        val ctx = Context.current().withValue(ContextKey.MDC, mdcMap)
        return Contexts.interceptCall(ctx, call, headers, next)
    }
}
