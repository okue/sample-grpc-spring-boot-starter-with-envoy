package com.okue.midori.extension

import com.github.marcoferrer.krotoplus.coroutines.GrpcContextElement
import com.okue.midori.constant.ContextKey
import kotlinx.coroutines.slf4j.MDCContext

fun GrpcContextElement.asMdcContext() = MDCContext(ContextKey.MDC.get(this.context))
