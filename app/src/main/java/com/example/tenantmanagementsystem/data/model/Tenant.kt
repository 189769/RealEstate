package com.example.tenantmanagementsystem.data.model

import java.io.Serializable

data class Tenant(
    val name: String,
    val phone: String,
    val rent: String
) : Serializable {
    val summary: String
        get() = "Tenant: $name\nPhone: $phone\nRent: KSh $rent"
}
