package com.microservices_example.orders.model

enum class OrderStatus {
    RELEASED,
    CANCELED,
    PAID,
    NOT_AUTHORIZED,
    APPROVED,
    READY,
    ON_DELIVERY,
    DELIVERED,
}
