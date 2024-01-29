package com.lira.credit.application.system.entities

import com.lira.credit.application.system.enumeration.Status
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate


@Entity
@Table(name = "tab_credit")
data class Credit(

        @Column(nullable = false, unique = true)
        val creditCode: java.util.UUID = java.util.UUID.randomUUID(),
        @Column(nullable = false)
        val creditValue: BigDecimal = BigDecimal.ZERO,
        @Column(nullable = false)
        val dayFirstInstallment: LocalDate,
        @Column(nullable = false)
        val numberOfInstallment: Int = 0,
        @Enumerated
        val status: Status = Status.IN_PROGRESS,
        @ManyToOne
        var customer: Customer? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
)
