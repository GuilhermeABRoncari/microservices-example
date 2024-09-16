package com.microservices_example.orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class OrdersApplication

fun main(args: Array<String>) {
	runApplication<OrdersApplication>(*args)
}
