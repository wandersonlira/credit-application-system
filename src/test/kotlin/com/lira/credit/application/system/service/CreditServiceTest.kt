package com.lira.credit.application.system.service

import com.lira.credit.application.system.entities.Credit
import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.exception.BusinessException
import com.lira.credit.application.system.repositories.CreditRepository
import com.lira.credit.application.system.service.impl.CreditService
import com.lira.credit.application.system.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK
    private lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    private lateinit var creditService: CreditService



    @Test
    fun `should create and save credit`() {

        //given
        val fakeCredit: Credit = buildCredit()
        val fakeCustomerId = 1L
        every { customerService.findById(fakeCustomerId) } returns fakeCredit.customer!!
        every { creditRepository.save(fakeCredit) } returns fakeCredit

        //when
        val actual: Credit = this.creditService.save(fakeCredit)

        //then
        verify(exactly = 1) {customerService.findById(fakeCustomerId)}
        verify(exactly = 1) {creditRepository.save(fakeCredit)}

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)

    }



//    @Test
//    fun `should not create credit when invalid day first installment`() {
//
//        //given
//        val invalidDayFirstInstallment: LocalDate = LocalDate.now().plusMonths(10)
//        val fakeCredit: Credit = buildCredit(dayFirstInstallment = invalidDayFirstInstallment)
//
//        every { creditRepository.save(fakeCredit) } answers {fakeCredit}
//
//        //when
//        Assertions.assertThatThrownBy { creditService.save(fakeCredit) }
//            .isInstanceOf(BusinessException::class.java)
//            .hasMessage("Invalid Date")
//        //then
//        verify(exactly = 0) { creditRepository.save(any()) }
//    }



    @Test
    fun `should return list of credit for a customer`() {
        //given
        val fakeCustomerId = 1L
        val expectedCredits: List<Credit> = listOf(buildCredit(), buildCredit(), buildCredit())

        every { creditRepository.findAllByCustomerID(fakeCustomerId) } returns expectedCredits

        //when
        val actual: List<Credit> = creditService.findAllByCustomer(fakeCustomerId)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isNotEmpty()
        Assertions.assertThat(actual).isSameAs(expectedCredits)

        verify(exactly = 1) { creditRepository.findAllByCustomerID(fakeCustomerId)}
    }



    @Test
    fun `should throw BusinessException for invalid credit code`() {
        //given
        val fakeCustomerId = 1L
        val invalidCreditCode: UUID = UUID.randomUUID()

        every { creditRepository.findByCreditCode(invalidCreditCode) } returns null

        //when
        //then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(fakeCustomerId, invalidCreditCode) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Credit $invalidCreditCode ING: not found, PT: Não encontrado")

        verify(exactly = 1) {creditRepository.findByCreditCode(invalidCreditCode)}

    }



    @Test
    fun `should throw IllegalArgumentException for different customer ID`() {
        //given
        val fakeCustomerId = 1L
        val fakeCreditCode: UUID = UUID.randomUUID()
        val fakeCredit: Credit = buildCredit(customer = Customer(idCustomer = 2L))

        every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit

        //when
        //then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(fakeCustomerId, fakeCreditCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Contact admin")

        verify { creditRepository.findByCreditCode(fakeCreditCode) }

    }




    companion object {
        private fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(1000.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2L),
            numberOfInstallment: Int = 7,
            customer: Customer = CustomerServiceTest.buildCustomer()

        ): Credit = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallment = numberOfInstallment,
            customer = customer
        )
    }



}