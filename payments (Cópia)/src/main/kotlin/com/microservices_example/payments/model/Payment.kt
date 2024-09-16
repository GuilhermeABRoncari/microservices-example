package com.microservices_example.payments.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotNull
    @field:Positive
    var amount: BigDecimal,

    @field:NotBlank
    @field:Size(max = 100)
    @field:Column(name = "user_name")
    var userName: String,

    @field:NotBlank
    @field:Size(max = 19)
    @field:Column(name = "card_number")
    var cardNumber: String,

    @field:NotBlank
    @field:Size(max = 7)
    var expiration: String,

    @field:NotBlank
    @field:Size(min = 3, max = 3)
    var cvv: String,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    var status: PaymentStatus,

    @field:NotNull
    @field:Column(name = "order_id")
    var orderId: Long,

    @field:NotNull
    @field:Column(name = "chosen_payment_id")
    var chosenPaymentId: Long
)
