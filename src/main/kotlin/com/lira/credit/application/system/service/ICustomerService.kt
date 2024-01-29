package com.lira.credit.application.system.service

import com.lira.credit.application.system.entities.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer
    fun findById(idCustomer: Long): Customer
    fun deleteById(idCustomer: Long)
}