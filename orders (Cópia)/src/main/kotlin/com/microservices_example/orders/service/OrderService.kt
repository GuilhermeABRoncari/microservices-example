package com.microservices_example.orders.service

import com.microservices_example.orders.dto.OrderDto
import com.microservices_example.orders.dto.OrderItemDto
import com.microservices_example.orders.dto.OrderStatusDto
import com.microservices_example.orders.http.PaymentClient
import com.microservices_example.orders.http.PaymentRequestBody
import com.microservices_example.orders.model.Order
import com.microservices_example.orders.model.OrderItem
import com.microservices_example.orders.model.OrderStatus
import com.microservices_example.orders.repository.OrderRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val paymentClient: PaymentClient
) {
    fun getAll(pageable: Pageable): Page<OrderDto> {
        val orders = orderRepository.findAll(pageable)
        return orders.map {
            OrderDto(
                id = it.id!!,
                dateTime = it.dateTime,
                status = it.status,
                items = it.items.map { item ->
                    OrderItemDto(
                        id = item.id!!,
                        quantity = item.quantity,
                        description = item.description
                    )
                }
            )
        }
    }

    fun getById(id: Long): OrderDto {
        val order = orderRepository.findById(id).orElseThrow { throw RuntimeException("Order not found") }
        return OrderDto(
            id = order.id!!,
            dateTime = order.dateTime,
            status = order.status,
            items = order.items.map { item ->
                OrderItemDto(
                    id = item.id!!,
                    quantity = item.quantity,
                    description = item.description
                )
            }
        )
    }

    fun create(orderDto: OrderDto): OrderDto {
        var order = Order(
            dateTime = LocalDateTime.now(),
            status = OrderStatus.RELEASED,
        )

        val orderItems = orderDto.items.map { item ->
            OrderItem(
                quantity = item.quantity,
                description = item.description,
                order = order
            )
        }
        order.items.plus(orderItems)
        order = orderRepository.save(order)

        paymentClient.createPayment(
            PaymentRequestBody(
                amount = 500.toBigDecimal(),
                userName = "John Doe",
                cardNumber = "1234567832145678",
                expiration = "12/32",
                cvv = "123",
                orderId = order.id!!,
                chosenPaymentId = 1
            )
        )

        return OrderDto(
            id = order.id!!,
            dateTime = order.dateTime,
            status = order.status,
            items = order.items.map { item ->
                OrderItemDto(
                    id = item.id!!,
                    quantity = item.quantity,
                    description = item.description
                )
            }
        )
    }

    fun updateStatus(id: Long, statusDto: OrderStatusDto) {
        val order = orderRepository.updateStatus(id, statusDto.status)
    }

    fun aprovePayment(id: Long) {
        val order = orderRepository.getByIdWithItems(id) ?: throw EntityNotFoundException("Order not found")
        order.status = OrderStatus.PAID
        orderRepository.updateStatus(id, OrderStatus.PAID)
        orderRepository.save(order)
    }

    fun delete(id: Long) {
        orderRepository.deleteById(id)
    }

    fun updateToPaid(id: Long) {
        val order = orderRepository.getById(id) ?: throw EntityNotFoundException("Order not found")
        order.status = OrderStatus.PAID
        orderRepository.updateStatus(id, OrderStatus.PAID)
        orderRepository.save(order)
    }
}
