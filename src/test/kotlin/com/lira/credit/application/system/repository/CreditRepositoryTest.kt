package com.lira.credit.application.system.repository

import com.lira.credit.application.system.entities.Credit
import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.entities.LocalAddress
import com.lira.credit.application.system.repositories.CreditRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setup () {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }



    @Test
    fun `should find customer by credit code`() {
        //given
        val creditCode1 = UUID.fromString("ac0665b6-40ea-4df4-b572-ece3ce371858")
        val creditCode2 = UUID.fromString("f0a3b1d9-1a17-486e-a1f5-cd2b53524eef")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        //when
        val fakeCreditCode1: Credit = creditRepository.findByCreditCode(creditCode1)!!
        val fakeCreditCode2: Credit = creditRepository.findByCreditCode(creditCode2)!!

        //then
        Assertions.assertThat(fakeCreditCode1).isNotNull
        Assertions.assertThat(fakeCreditCode2).isNotNull
        Assertions.assertThat(fakeCreditCode1).isSameAs(credit1)
        Assertions.assertThat(fakeCreditCode2).isSameAs(credit2)

    }



    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallment = numberOfInstallments,
        customer = customer
    )


    private fun buildCustomer(
        firstName: String = "Zezinho Panda",
        lastName: String = "Sentinela",
        cpf: String = "14328128078",
        email: String = "panda@email.com",
        password: String = "pandinha123",
        zipCode: String = "50030-150",
        street: String = "Rua dos pandinhas sentinela",
        income: BigDecimal = BigDecimal.valueOf(1000.0),

    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = LocalAddress(
            zipCode = zipCode,
            street = street
        ),
        income = income
    )


}