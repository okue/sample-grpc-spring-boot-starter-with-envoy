package com.okue.demo.midori.grpc.service

import com.okue.demo.midori.grpc.common.Result
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class GreetingGrpcService : GreetingGrpc.GreetingImplBase() {
    private val log = KotlinLogging.logger {}

    override fun hello(request: HelloRequest?, responseObserver: StreamObserver<HelloResponse>?) {
        requireNotNull(request)
        requireNotNull(responseObserver)
        log.info("hello is called with $request")

        val result = Result.newBuilder().setStatus(Result.Status.Ok)
        val response = HelloResponse
            .newBuilder()
            .setMessage("I got ${request.message} from ${request.from.id}")
            .setResult(result)
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}