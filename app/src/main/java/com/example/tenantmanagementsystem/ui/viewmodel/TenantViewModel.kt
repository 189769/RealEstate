package com.example.tenantmanagementsystem.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tenantmanagementsystem.data.model.Tenant
import com.example.tenantmanagementsystem.data.repository.TenantRepository

class TenantViewModel : ViewModel() {

    // Holds the current list of tenants
    private val _tenants = MutableLiveData<List<Tenant>>(
        TenantRepository.tenants.toList()
    )
    val tenants: LiveData<List<Tenant>> = _tenants

    // Holds the total number of tenants
    private val _tenantCountText = MutableLiveData<String>()
    val tenantCountText: LiveData<String> = _tenantCountText

    // Holds the total monthly rent
    private val _totalRentText = MutableLiveData<String>()
    val totalRentText: LiveData<String> = _totalRentText

    // Holds formatted tenant information
    private val _allTenantsText = MutableLiveData<String>()
    val allTenantsText: LiveData<String> = _allTenantsText

    // Initialize tenant information
    init {
        refreshTenantSummaries()
    }

    // Add a new tenant
    fun addTenant(tenant: Tenant) {
        TenantRepository.tenants.add(tenant)

        // Update LiveData with the latest tenants
        _tenants.value = TenantRepository.tenants.toList()

        // Update tenant count, rent and summaries
        refreshTenantSummaries()
    }

    // Refresh all observable tenant information
    private fun refreshTenantSummaries() {
        val currentTenants = _tenants.value.orEmpty()

        _tenantCountText.value =
            "Tenant Count: ${currentTenants.size}"

        _totalRentText.value =
            "Total Monthly Rent: KSh ${TenantRepository.totalRent()}"

        _allTenantsText.value =
            currentTenants.joinToString(separator = "\n\n") {
                it.summary
            }
    }
}