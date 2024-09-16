package com.microservices_example.orders.http

import java.math.BigDecimal

data class PaymentRequestBody(
    var amount: BigDecimal,
    var userName: String,
    var cardNumber: String,
    var expiration: String,
    var cvv: String,
    var orderId: Long,
    var chosenPaymentId: Long
)

