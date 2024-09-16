package com.microservices_example.orders.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotNull
    @field:Positive
    val quantity: Int,

    val description: String? = null,

    @ManyToOne(optional = false)
    var order: Order
)
