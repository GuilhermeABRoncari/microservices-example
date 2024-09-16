package com.microservices_example.payments.dto

import com.microservices_example.payments.model.PaymentStatus

data class PaymentDto(
    val id: Long,
    val amount: String,
    val userName: String,
    val cardNumber: String,
    val expiration: String,
    val cvv: String,
    val status: PaymentStatus?,
    val orderId: Long,
    val chosenPaymentId: Long
)
