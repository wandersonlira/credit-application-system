package com.lira.credit.application.system.dto


import com.lira.credit.application.system.entities.Credit
import java.math.BigDecimal
import java.util.*

data class CreditViewList (

    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallment: Int
) {

    constructor(credit: Credit) : this (
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallment = credit.numberOfInstallment
    )

}
