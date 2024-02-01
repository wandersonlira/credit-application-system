package com.lira.credit.application.system.dto

import com.lira.credit.application.system.entities.Customer
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CustomerUpdateDTO(

    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val firstName: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val lastName: String,
    @field:NotNull(message = "ING: Invalid 'null' input, PT: Entrada 'vazio' inválida") val income: BigDecimal,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val zipCode: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val street: String
) {

    fun toEntity(customer: Customer): Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.zipCode = this.zipCode
        customer.address.street = this.street

        return customer
    }
}