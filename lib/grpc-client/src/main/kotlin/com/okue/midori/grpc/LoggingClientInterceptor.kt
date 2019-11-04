package com.okue.midori.grpc

import com.okue.midori.constant.ContextKey
import com.okue.midori.constant.MdcKey
import com.okue.midori.constant.MetadataKey
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall
import io.grpc.Metadata
import io.grpc.MethodDescriptor

class LoggingClientInterceptor : ClientInterceptor {
    override fun <ReqT, RespT> interceptCall(
        method: MethodDescriptor<ReqT, RespT>?,
        callOptions: CallOptions?,
        next: Channel?
    ): ClientCall<ReqT, RespT> {
        return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
            next?.newCall(method, callOptions)
        ) {
            override fun start(responseListener: ClientCall.Listener<RespT>, headers: Metadata) {
                val mdc = ContextKey.MDC.get()
                if (mdc != null) {
                    headers.put(MetadataKey.ORIGINAL_PATH, mdc[MdcKey.ORIGINAL_PATH])
                    headers.put(MetadataKey.REQUEST_ID, mdc[MdcKey.REQUEST_ID])
                }
                super.start(responseListener, headers)
            }
        }
    }
}
