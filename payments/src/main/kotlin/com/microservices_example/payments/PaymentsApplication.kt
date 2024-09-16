package com.microservices_example.payments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class PaymentsApplication

fun main(args: Array<String>) {
	runApplication<PaymentsApplication>(*args)
}
