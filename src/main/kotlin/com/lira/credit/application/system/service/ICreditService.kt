package com.lira.credit.application.system.service

import com.lira.credit.application.system.entities.Credit
import java.util.*


interface ICreditService {

    fun save(credit: Credit): Credit
    fun findAllByCustomer(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit

}