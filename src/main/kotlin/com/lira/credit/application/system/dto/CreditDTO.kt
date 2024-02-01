package com.lira.credit.application.system.dto

import com.lira.credit.application.system.entities.Credit
import com.lira.credit.application.system.entities.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO(

    @field:NotNull(message = "ING: Invalid 'null' input, PT: Entrada 'vazio' inválida") val creditValue: BigDecimal,
    @field:Future(message = "ING: Day first < now, PT: Primeira data menor que atual") val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,
    @field:NotNull(message = "ING: Invalid 'null' input, PT: Entrada 'vazio' inválida") val customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallment = this.numberOfInstallments,
        customer = Customer(idCustomer = this.customerId)
    )

}
