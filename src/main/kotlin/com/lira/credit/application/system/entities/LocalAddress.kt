package com.lira.credit.application.system.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class LocalAddress(

        @Column(nullable = false)
        var zipCode: String = "",
        @Column(nullable = false)
        var street: String = ""
)
