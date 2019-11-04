package com.okue.midori.api.grpc.service

import com.github.marcoferrer.krotoplus.coroutines.GrpcContextElement
import com.okue.midori.extension.asMdcContext
import com.okue.midori.grpc.service.BookingCoroutineGrpc
import com.okue.midori.grpc.service.BookingInternalCoroutineGrpc.BookingInternalCoroutineStub
import com.okue.midori.grpc.service.BookingListRequest
import com.okue.midori.grpc.service.BookingListResponse
import kotlinx.coroutines.Dispatchers
import mu.KotlinLogging
import org.lognet.springboot.grpc.GRpcService
import kotlin.coroutines.CoroutineContext

@GRpcService
class BookingGrpcService(
    private val stub: BookingInternalCoroutineStub
) : BookingCoroutineGrpc.BookingImplBase() {
    private val log = KotlinLogging.logger {}

    override val initialContext: CoroutineContext
        get() = Dispatchers.IO + GrpcContextElement().asMdcContext()

    override suspend fun bookingList(request: BookingListRequest): BookingListResponse {
        log.info("bookingList is called with $request")
        return stub.bookingList(request)
    }
}
