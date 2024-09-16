package com.microservices_example.orders.dto

data class OrderItemDto(
    val id: Long,
    val quantity: Int,
    val description: String?
)
