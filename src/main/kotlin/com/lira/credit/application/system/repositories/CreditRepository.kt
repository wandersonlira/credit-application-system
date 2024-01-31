package com.lira.credit.application.system.repositories

import com.lira.credit.application.system.entities.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditRepository : JpaRepository<Credit, Long> {

    fun findByCreditCode(creditCode: UUID): Credit?

    @Query(value = "SELECT * FROM tab_credit WHERE customer_id_customer = ?1", nativeQuery = true)
    fun findAllByCustomerID(customerId: Long): List<Credit>
}