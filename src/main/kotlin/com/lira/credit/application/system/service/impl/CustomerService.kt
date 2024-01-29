package com.lira.credit.application.system.service.impl

import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.repositories.CustomerRepository
import com.lira.credit.application.system.service.ICustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService (

    @Autowired
    private val customerRepository: CustomerRepository

) : ICustomerService{

    override fun save(customer: Customer): Customer =
        this.customerRepository.save(customer)

    override fun findById(idCustomer: Long): Customer = this.customerRepository.findById(idCustomer).orElseThrow() {
        throw RuntimeException("IdCustomer {$idCustomer} not found")
    }

    override fun deleteById(idCustomer: Long) =
        this.customerRepository.deleteById(idCustomer)
}