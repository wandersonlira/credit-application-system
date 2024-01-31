package com.lira.credit.application.system.dto

import com.lira.credit.application.system.entities.Customer
import java.math.BigDecimal

data class CustomerView (

    val firstName: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val zipCode: String,
    val street: String,
//    val idCustomer: Long?
) {

    constructor(customer: Customer): this (

        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        income = customer.income,
        email = customer.email,
        zipCode = customer.address.zipCode,
        street = customer.address.street,
//        idCustomer = customer.idCustomer
    )

}
