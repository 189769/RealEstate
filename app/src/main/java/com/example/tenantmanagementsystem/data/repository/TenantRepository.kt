
package com.example.tenantmanagementsystem.data.repository

import androidx.lifecycle.LiveData
import com.example.tenantmanagementsystem.data.local.TenantDao
import com.example.tenantmanagementsystem.data.model.Tenant

class TenantRepository(
    private val tenantDao: TenantDao
) {

    // Observe all tenants stored in Room
    val tenants: LiveData<List<Tenant>> =
        tenantDao.getAllTenants()

    // Save a tenant into Room Database
    suspend fun addTenant(tenant: Tenant) {
        tenantDao.insertTenant(tenant)
    }

    // Calculate the total monthly rent
    fun totalRent(tenants: List<Tenant>): Double {
        return tenants.sumOf {
            it.rent.toDoubleOrNull() ?: 0.0
        }
    }
}
