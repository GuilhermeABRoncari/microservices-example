package com.microservices_example.payments.model

enum class PaymentStatus {
    CREATED,
    APPROVED,
    APPROVED_WITHOUT_INTEGRATION,
    CANCELED,
}
