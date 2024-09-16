package com.microservices_example.orders.dto

import com.microservices_example.orders.model.OrderStatus

data class OrderStatusDto (
    val status: OrderStatus
)
