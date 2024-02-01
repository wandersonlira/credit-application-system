package com.lira.credit.application.system.entities

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "tab_customer")
data class Customer(

        @Column(nullable = false)
        var firstName: String = "",
        @Column(nullable = false)
        var lastName: String = "",
        @Column(nullable = false, unique = true)
        var cpf: String = "",
        @Column(nullable = false)
        var income: BigDecimal = BigDecimal.ZERO,
        @Column(nullable = false, unique = true)
        var email: String = "",
        @Column(nullable = false)
        var password: String = "",
        @Column(nullable = false)
        @Embedded
        var address: LocalAddress = LocalAddress(),
        @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE, CascadeType.PERSIST), mappedBy = "customer")
        var credit: List<Credit> = mutableListOf(),
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val idCustomer: Long? = null
)
