
package com.example.tenantmanagementsystem.ui.tenants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystem.R
import com.example.tenantmanagementsystem.data.model.Tenant
import com.example.tenantmanagementsystem.databinding.FragmentAddTenantBinding
import com.example.tenantmanagementsystem.ui.viewmodel.TenantViewModel

class AddTenantFragment : Fragment() {

    private var _binding: FragmentAddTenantBinding? = null
    private val binding get() = _binding!!

    // Share the same ViewModel with DashboardFragment
    private val tenantViewModel: TenantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTenantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {

            val name = binding.tenantNameEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val rent = binding.rentEditText.text.toString().trim()

            // Validate tenant name
            binding.tenantNameEditText.error =
                if (name.isEmpty()) "Enter tenant name" else null

            // Validate phone number
            binding.phoneEditText.error =
                if (phone.isEmpty()) "Enter phone number" else null

            // Validate monthly rent
            val rentAmount = rent.toDoubleOrNull()

            binding.rentEditText.error = when {
                rent.isEmpty() -> "Enter monthly rent"
                rentAmount == null ||
                        !rentAmount.isFinite() ||
                        rentAmount <= 0.0 ->
                    "Enter a positive rent amount"
                else -> null
            }

            // Stop if validation fails
            if (
                binding.tenantNameEditText.error != null ||
                binding.phoneEditText.error != null ||
                binding.rentEditText.error != null
            ) {
                return@setOnClickListener
            }

            // Create tenant
            val tenant = Tenant(
                name = name,
                phone = phone,
                rent = rent
            )

            // Save through the shared ViewModel
            tenantViewModel.addTenant(tenant)

            Toast.makeText(
                requireContext(),
                "Tenant added successfully",
                Toast.LENGTH_SHORT
            ).show()

            // Navigate to tenant list
            findNavController().navigate(
                R.id.action_addTenantFragment_to_viewTenantsFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
