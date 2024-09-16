package com.microservices_example.payments.service

import com.microservices_example.payments.dto.PaymentDto
import com.microservices_example.payments.http.OrderClient
import com.microservices_example.payments.model.Payment
import com.microservices_example.payments.model.PaymentStatus
import com.microservices_example.payments.repository.PaymentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val orderClient: OrderClient
) {

    fun getAll(pageable: Pageable): Page<PaymentDto> {
        val payments = paymentRepository.findAll(pageable)
        return payments.map {
            PaymentDto(
                id = it.id!!,
                amount = it.amount.toString(),
                userName = it.userName,
                cardNumber = it.cardNumber,
                expiration = it.expiration,
                cvv = it.cvv,
                status = it.status,
                orderId = it.orderId,
                chosenPaymentId = it.chosenPaymentId
            )
        }
    }

    fun getById(id: Long): PaymentDto {
        val payment = paymentRepository.findById(id).orElseThrow { throw RuntimeException("Payment not found") }
        return PaymentDto(
            id = payment.id!!,
            amount = payment.amount.toString(),
            userName = payment.userName,
            cardNumber = payment.cardNumber,
            expiration = payment.expiration,
            cvv = payment.cvv,
            status = payment.status,
            orderId = payment.orderId,
            chosenPaymentId = payment.chosenPaymentId
        )
    }

    fun create(paymentDto: PaymentDto): PaymentDto {
        val payment = paymentRepository.save(
            Payment(
                amount = paymentDto.amount.toBigDecimal(),
                userName = paymentDto.userName,
                cardNumber = paymentDto.cardNumber,
                expiration = paymentDto.expiration,
                cvv = paymentDto.cvv,
                status = PaymentStatus.CREATED,
                orderId = paymentDto.orderId,
                chosenPaymentId = paymentDto.chosenPaymentId
            )
        )
        return PaymentDto(
            id = payment.id!!,
            amount = payment.amount.toString(),
            userName = payment.userName,
            cardNumber = payment.cardNumber,
            expiration = payment.expiration,
            cvv = payment.cvv,
            status = payment.status,
            orderId = payment.orderId,
            chosenPaymentId = payment.chosenPaymentId
        )
    }

    fun update(id: Long, paymentDto: PaymentDto): PaymentDto {
        val payment = paymentRepository.findById(id).orElseThrow { throw RuntimeException("Payment not found") }
        payment.amount =
            if (payment.amount != paymentDto.amount.toBigDecimal()) paymentDto.amount.toBigDecimal() else payment.amount
        payment.userName = if (payment.userName != paymentDto.userName) paymentDto.userName else payment.userName
        payment.cardNumber =
            if (payment.cardNumber != paymentDto.cardNumber) paymentDto.cardNumber else payment.cardNumber
        payment.expiration =
            if (payment.expiration != paymentDto.expiration) paymentDto.expiration else payment.expiration
        payment.cvv = if (payment.cvv != paymentDto.cvv) paymentDto.cvv else payment.cvv
        payment.status = paymentDto.status ?: payment.status
        payment.orderId = if (payment.orderId != paymentDto.orderId) paymentDto.orderId else payment.orderId
        payment.chosenPaymentId =
            if (payment.chosenPaymentId != paymentDto.chosenPaymentId) paymentDto.chosenPaymentId else payment.chosenPaymentId

        paymentRepository.save(payment)

        return PaymentDto(
            id = payment.id!!,
            amount = payment.amount.toString(),
            userName = payment.userName,
            cardNumber = payment.cardNumber,
            expiration = payment.expiration,
            cvv = payment.cvv,
            status = payment.status,
            orderId = payment.orderId,
            chosenPaymentId = payment.chosenPaymentId
        )
    }

    fun delete(id: Long) {
        paymentRepository.deleteById(id)
    }

    fun confirmPayment(id: Long) {
        val payment = paymentRepository.findById(id).orElseThrow { throw RuntimeException("Payment not found") }
        payment.status = PaymentStatus.APPROVED
        paymentRepository.save(payment)
        orderClient.updateOrderToPaid(payment.orderId)
    }

    fun paymentConfirmationNotIntegrated(id: Long) {
        val payment = paymentRepository.findById(id).orElseThrow { throw RuntimeException("Payment not found") }
        payment.status = PaymentStatus.APPROVED_WITHOUT_INTEGRATION
        paymentRepository.save(payment)
    }
}
