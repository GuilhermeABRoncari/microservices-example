package com.microservices_example.payments.controller

import com.microservices_example.payments.dto.PaymentDto
import com.microservices_example.payments.service.PaymentService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/payments")
class PaymentController(
    private val paymentService: PaymentService
) {
    @GetMapping("/port")
    fun port(@Value("\${local.server.port}") port: String): String {
        return String.format("Response from instance with port: %s", port)
    }
    @GetMapping
    fun getAll(@PageableDefault(size = 10) pageable: Pageable) = paymentService.getAll(pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable @NotNull id: Long) = paymentService.getById(id)

    @PostMapping
    fun create(
        @RequestBody
        @Valid
        paymentDto: PaymentDto,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<PaymentDto> {
        val createdPayment = paymentService.create(paymentDto)
        val uri = uriComponentsBuilder.path("/payments/{id}").buildAndExpand(createdPayment.id).toUri()
        return ResponseEntity.created(uri).body(createdPayment)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable @NotNull id: Long,
        @RequestBody @Valid paymentDto: PaymentDto
    ) = paymentService.update(id, paymentDto)

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "confirmPaymentEndPoint", fallbackMethod = "orderPaidWithPendingIntegration")
    fun confirm(@PathVariable @NotNull id: Long) = paymentService.confirmPayment(id)

    fun orderPaidWithPendingIntegration(id: Long, e: Exception) {
        return paymentService.paymentConfirmationNotIntegrated(id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable @NotNull id: Long) = paymentService.delete(id)
}
