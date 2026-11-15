
package com.example.tenantmanagementsystem.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tenantmanagementsystem.data.local.TenantDatabase
import com.example.tenantmanagementsystem.data.model.Tenant
import com.example.tenantmanagementsystem.data.repository.TenantRepository
import kotlinx.coroutines.launch

class TenantViewModel(application: Application) :
    AndroidViewModel(application) {

    // Connect to Room Database
    private val tenantDao =
        TenantDatabase.getDatabase(application).tenantDao()

    private val repository = TenantRepository(tenantDao)

    // Observe tenants stored in Room
    val tenants: LiveData<List<Tenant>> = repository.tenants

    // Dashboard information
    private val _tenantCountText = MutableLiveData<String>()
    val tenantCountText: LiveData<String> = _tenantCountText

    private val _totalRentText = MutableLiveData<String>()
    val totalRentText: LiveData<String> = _totalRentText

    private val _allTenantsText = MutableLiveData<String>()
    val allTenantsText: LiveData<String> = _allTenantsText

    init {
        tenants.observeForever(::updateTenantSummaries)
    }

    // Save a tenant into Room Database
    fun addTenant(tenant: Tenant) {
        viewModelScope.launch {
            repository.addTenant(tenant)
        }
    }

    // Update dashboard and tenant summaries
    private fun updateTenantSummaries(
        currentTenants: List<Tenant>
    ) {
        _tenantCountText.value =
            "Tenant Count: ${currentTenants.size}"

        _totalRentText.value =
            "Total Monthly Rent: KSh ${
                repository.totalRent(currentTenants)
            }"

        _allTenantsText.value =
            currentTenants.joinToString("\n\n") {
                it.summary
            }
    }

    override fun onCleared() {
        tenants.removeObserver(::updateTenantSummaries)
        super.onCleared()
    }
}
