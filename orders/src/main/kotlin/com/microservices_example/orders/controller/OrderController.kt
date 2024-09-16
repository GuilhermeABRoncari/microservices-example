package com.microservices_example.orders.controller

import com.microservices_example.orders.dto.OrderDto
import com.microservices_example.orders.dto.OrderStatusDto
import com.microservices_example.orders.service.OrderService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping("/port")
    fun port(@Value("\${local.server.port}") port: String): String {
        return String.format("Response from instance with port: %s", port)
    }

    @GetMapping
    fun getAll(@PageableDefault(size = 10) pageable: Pageable): Page<OrderDto> = orderService
        .getAll(pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable @NotNull id: Long): OrderDto = orderService.getById(id)

    @PostMapping
    fun create(
        @RequestBody
        @Valid
        orderDto: OrderDto,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<OrderDto> {
        val createdOrder = orderService.create(orderDto)
        val uri = uriComponentsBuilder.path("/orders/{id}").buildAndExpand(createdOrder.id).toUri()
        return ResponseEntity.created(uri).body(createdOrder)
    }

    @PutMapping("/{id}/status")
    fun update(
        @PathVariable @NotNull id: Long,
        @RequestBody @Valid orderStatusDto: OrderStatusDto
    ) = orderService.updateStatus(id, orderStatusDto)

    @PutMapping("/{id}/paid")
    fun updatePaid(@PathVariable @NotNull id: Long) = orderService.updateToPaid(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable @NotNull id: Long) = orderService.delete(id)
}
