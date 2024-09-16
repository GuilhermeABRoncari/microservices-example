package com.microservices_example.payments.http

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "order-ms")
interface OrderClient {
    @RequestMapping(method = [RequestMethod.PUT], value = ["/orders/{id}/paid"])
    fun updateOrderToPaid(@PathVariable id: Long)
}
