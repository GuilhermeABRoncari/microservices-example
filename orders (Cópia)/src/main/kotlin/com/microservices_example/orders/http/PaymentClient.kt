package com.microservices_example.orders.http

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "payment-ms")
interface PaymentClient {
    @RequestMapping(method = [RequestMethod.POST], value = ["/payments"])
    fun createPayment(
        @RequestBody
        requestBody: PaymentRequestBody
    )
}
