package com.okue.midori.internal.api.grpc.service

import com.github.marcoferrer.krotoplus.coroutines.GrpcContextElement
import com.okue.midori.extension.asMdcContext
import com.okue.midori.grpc.common.Result
import com.okue.midori.grpc.service.BookingInternalCoroutineGrpc
import com.okue.midori.grpc.service.BookingListRequest
import com.okue.midori.grpc.service.BookingListResponse
import com.okue.midori.grpc.service.result
import kotlinx.coroutines.Dispatchers
import mu.KotlinLogging
import org.lognet.springboot.grpc.GRpcService
import kotlin.coroutines.CoroutineContext

@GRpcService
class BookingInternalGrpcService : BookingInternalCoroutineGrpc.BookingInternalImplBase() {
    private val log = KotlinLogging.logger {}

    override val initialContext: CoroutineContext
        get() = Dispatchers.IO + GrpcContextElement().asMdcContext()

    override suspend fun bookingList(request: BookingListRequest): BookingListResponse {
        log.info("bookingList is called with $request")
        return BookingListResponse {
            result { status = Result.Status.Ok }
            addAllName(listOf("a", "b", "c"))
        }
    }
}
