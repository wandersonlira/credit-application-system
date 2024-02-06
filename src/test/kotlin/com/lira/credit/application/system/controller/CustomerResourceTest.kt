package com.lira.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lira.credit.application.system.dto.CustomerDTO
import com.lira.credit.application.system.dto.CustomerUpdateDTO
import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.repositories.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import kotlin.random.Random


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    companion object {
        const val URL: String = "/api/customers"
    }


    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()


    @Test
    fun `should create a customer and return 201 status`() {
        //given
        val customerDTO: CustomerDTO = buildCustomerDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Zezinho Panda"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Sentinela"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("74786890863"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("pandinha@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(400.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("50030-150"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua dos pandinhas sentinela"))
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should not save a customer with same CPF and return 409 status`() {
        //given
        customerRepository.save(buildCustomerDTO().toEntity())
        val customerDTO: CustomerDTO = buildCustomerDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Conflit! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("[class org.springframework.dao.DataIntegrityViolationException]")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should not save a customer with firstName empty and return 400 status`() {
        //given
        val customerDTO: CustomerDTO = buildCustomerDTO(firstName = "")
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL).content(valueAsString)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("[class org.springframework.web.bind.MethodArgumentNotValidException], Object: customerDTO")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should find customer by id and return 200 status`() {
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${customer.idCustomer}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Zezinho Panda"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Sentinela"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("74786890863"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("pandinha@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(400.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("50030-150"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua dos pandinhas sentinela"))
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should not find customer whith invalid id and return 400 status`() {
        //given
        val invalid: Long = 2L
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$invalid")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.lira.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should delete by id and return 204 status`() {
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${customer.idCustomer}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should not delete customer by id and return 400 status`() {
        //given
        val invalid: Long = Random.nextLong()
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${invalid}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.lira.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should update a customer and return 200 status`() {
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        val customerUpdateDTO: CustomerUpdateDTO = buildCustomerUpdadeDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDTO)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=${customer.idCustomer}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Zezinho Panda-Update"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Sentinela-Update"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("74786890863"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("pandinha@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(400.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("50030-150-Update"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua dos pandinhas sentinela-Update"))
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `should not update a customer with invalid id and return 400 status`() {
        //given
        val invalid: Long = Random.nextLong()
        val customerUpdateDTO: CustomerUpdateDTO = buildCustomerUpdadeDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDTO)
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?custumerId=$invalid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
//            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.lira.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())

    }

    private fun buildCustomerUpdadeDTO(
        firstName: String = "Zezinho Panda-Update",
        lastName: String = "Sentinela-Update",
        income: BigDecimal = BigDecimal.valueOf(400.0),
        zipCode: String = "50030-150-Update",
        street: String = "Rua dos pandinhas sentinela-Update"

    ): CustomerUpdateDTO = CustomerUpdateDTO(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street
    )


    private fun buildCustomerDTO(
        firstName: String = "Zezinho Panda",
        lastName: String = "Sentinela",
        cpf: String = "74786890863",
        email: String = "pandinha@email.com",
        income: BigDecimal = BigDecimal.valueOf(1200.0),
        password: String = "pandinha123",
        zipCode: String = "50030-150",
        street: String = "Rua dos pandinhas sentinela"

    ) = CustomerDTO(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        income = income,
        password = password,
        zipCode = zipCode,
        street = street
    )

}