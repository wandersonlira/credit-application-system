package com.lira.credit.application.system.controller

import com.lira.credit.application.system.dto.CustomerDTO
import com.lira.credit.application.system.dto.CustomerUpdateDTO
import com.lira.credit.application.system.dto.CustomerView
import com.lira.credit.application.system.entities.Customer
import com.lira.credit.application.system.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerResource(

    private val customerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDTO): ResponseEntity<String> {
        val savedCustomer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer ${savedCustomer.email} saved!")
    }


    @GetMapping("/{idCustomer}")
    fun findById(@PathVariable idCustomer: Long): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerService.findById(idCustomer)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
    }


    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long) = this.customerService.deleteById(id)


    @PatchMapping
    fun updateCustomer(
        @RequestParam(value = "customerId") idCustomer: Long,
        @RequestBody customerUpdateDTO: CustomerUpdateDTO
    ): ResponseEntity<CustomerView> {
        val customer: Customer = customerService.findById(idCustomer)
        val customerToUpdate: Customer = customerUpdateDTO.toEntity(customer)
        val customerUpdated: Customer = this.customerService.save(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
    }
}