package com.microservices_example.orders.repository

import com.microservices_example.orders.model.Order
import com.microservices_example.orders.model.OrderStatus
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.status = :status where o.id = :id")
    fun updateStatus(id: Long, status: OrderStatus)

    @Query("select o from Order o join fetch o.items where o.id = :id")
    fun getByIdWithItems(id: Long): Order
}
