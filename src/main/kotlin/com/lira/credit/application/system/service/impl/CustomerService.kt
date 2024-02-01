package com.lira.credit.application.system.service.impl

import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.exception.BusinessException
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
        throw BusinessException("IdCustomer {$idCustomer} ING: not found, PT: não encontrado")
    }

    override fun deleteById(idCustomer: Long) {
        val customer: Customer = this.findById(idCustomer)
        this.customerRepository.delete(customer)
    }
}