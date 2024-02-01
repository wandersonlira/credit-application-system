package com.lira.credit.application.system.service.impl

import com.lira.credit.application.system.entities.Credit
import com.lira.credit.application.system.exception.BusinessException
import com.lira.credit.application.system.repositories.CreditRepository
import com.lira.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(

    private val creditRepository: CreditRepository,
    private val customerService: CustomerService

) : ICreditService {


    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.idCustomer!!)
        }
        return this.creditRepository.save(credit)
    }


    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomerID(customerId)


    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
         val credit: Credit = (this.creditRepository.findByCreditCode(creditCode)
             ?: throw BusinessException("Credit $creditCode ING: not found, PT: Não encontrado"))
        return if (credit.customer?.idCustomer == customerId) credit else throw IllegalArgumentException("Contact admin")
    }
}