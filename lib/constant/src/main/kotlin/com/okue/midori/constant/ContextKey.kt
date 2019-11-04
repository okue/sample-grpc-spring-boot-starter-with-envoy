package com.okue.midori.constant

import io.grpc.Context
import kotlinx.coroutines.slf4j.MDCContextMap

object ContextKey {
    val MDC: Context.Key<MDCContextMap> = Context.key("mdc")
}
