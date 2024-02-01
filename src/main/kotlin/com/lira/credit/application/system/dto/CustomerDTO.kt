package com.lira.credit.application.system.dto

import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.entities.LocalAddress
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDTO(

    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val firstName: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val lastName: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida")
    @field:CPF(message = "ING: this invalid CPF, PT: CPF inválido") val cpf: String,
    @field:NotNull(message = "ING: Invalid 'null' input, PT: Entrada 'vazio' inválida") val income: BigDecimal,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida")
    @field:Email(message = "ING: Invalid input, PT: Entrada inválida") val email: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val password: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val zipCode: String,
    @field:NotEmpty(message = "ING: Invalid input, PT: Entrada inválida") val street: String
) {

    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = LocalAddress(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}
