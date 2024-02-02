package com.lira.credit.application.system.service

import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.entities.LocalAddress
import com.lira.credit.application.system.exception.BusinessException
import com.lira.credit.application.system.repositories.CustomerRepository
import com.lira.credit.application.system.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*


@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    lateinit var customerRepository: CustomerRepository
    @InjectMockKs
    lateinit var customerService: CustomerService


    @Test
    fun `should create customer`() {
        // given
        val fakeCustomer: Customer = buildCustomer()
        every { customerRepository.save(any()) } returns fakeCustomer

        //when
        val actual: Customer = customerService.save(fakeCustomer)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.save(fakeCustomer) }

    }



    @Test
    fun `should find customer by id_customer`() {
        // given
        val fakeIdCustomer: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(idCustomer = fakeIdCustomer)
        every { customerRepository.findById(fakeIdCustomer) } returns Optional.of(fakeCustomer)

        //when
        val actual: Customer = customerService.findById(fakeIdCustomer)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(fakeIdCustomer) }

    }



    @Test
    fun `should not find customer by id_customer and throw BusinessException`() {

        //given
        val fakeIdCustomer: Long = Random().nextLong()
        every { customerRepository.findById(fakeIdCustomer) } returns Optional.empty()

        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeIdCustomer) }
            .withMessage("IdCustomer {$fakeIdCustomer} ING: not found, PT: não encontrado")
        verify(exactly = 1) { customerRepository.findById(fakeIdCustomer) }

    }



    @Test
    fun `should delete customer by id_customer`() {
        //given
        val fakeIdCustomer: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(idCustomer = fakeIdCustomer)
        every { customerRepository.findById(fakeIdCustomer) } returns Optional.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer) } just runs

        //when
        customerService.deleteById(fakeIdCustomer)

        //then
        verify(exactly = 1) { customerRepository.findById(fakeIdCustomer) }
        verify(exactly = 1) { customerRepository.delete(fakeCustomer) }


    }

    private fun buildCustomer(
        firstName: String = "Zezinho Panda",
        lastName: String = "Sentinela",
        cpf: String = "14328128078",
        email: String = "panda@email.com",
        password: String = "pandinha123",
        zipCode: String = "50030-150",
        street: String = "Rua dos pandinhas sentinela",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        idCustomer: Long = 1L

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
        income = income,
        idCustomer = idCustomer
    )


}