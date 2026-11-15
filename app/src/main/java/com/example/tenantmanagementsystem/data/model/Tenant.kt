package com.example.tenantmanagementsystem.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tenants")
data class Tenant(
    val name: String,
    val phone: String,
    val rent: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

) : Serializable {

    @get:Ignore
    val summary: String
        get() = "Tenant: $name\nPhone: $phone\nRent: KSh $rent"
}