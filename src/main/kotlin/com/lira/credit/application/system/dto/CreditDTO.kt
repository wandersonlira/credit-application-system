package com.lira.credit.application.system.dto

import com.lira.credit.application.system.entities.Credit
import com.lira.credit.application.system.entities.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO (

    val creditValue: BigDecimal,
    val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,
    val customerId: Long
) {

    fun toEntity(): Credit = Credit (
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallment = this.numberOfInstallments,
        customer = Customer(idCustomer = this.customerId)
    )

}
