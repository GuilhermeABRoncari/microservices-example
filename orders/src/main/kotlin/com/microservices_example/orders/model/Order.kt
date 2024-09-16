package com.microservices_example.orders.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotNull
    var dateTime: LocalDateTime? = null,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null,

    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "order")
    var items: List<OrderItem> = mutableListOf()
) {
    constructor() : this(null, null, null, mutableListOf())
}
