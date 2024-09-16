package com.microservices_example.orders.dto

import com.microservices_example.orders.model.OrderStatus
import jakarta.persistence.Column
import java.time.LocalDateTime

data class OrderDto(
    val id: Long,
    @field:Column(name = "date_time")
    val dateTime: LocalDateTime?,
    val status: OrderStatus?,
    val items: List<OrderItemDto>
)
