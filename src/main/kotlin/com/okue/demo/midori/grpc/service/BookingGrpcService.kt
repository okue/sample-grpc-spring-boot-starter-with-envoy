package com.okue.demo.midori.grpc.service

import com.okue.demo.midori.grpc.common.Result
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class BookingGrpcService : BookingGrpc.BookingImplBase() {
    private val log = KotlinLogging.logger {}

    override fun bookingList(request: BookingListRequest?, responseObserver: StreamObserver<BookingListResponse>?) {
        requireNotNull(request)
        requireNotNull(responseObserver)
        log.info("bookingList is called with $request")

        val result = Result.newBuilder().setStatus(Result.Status.Ok)
        val response = BookingListResponse
            .newBuilder()
            .setResult(result)
            .addAllName(listOf("a", "b", "c"))
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}
