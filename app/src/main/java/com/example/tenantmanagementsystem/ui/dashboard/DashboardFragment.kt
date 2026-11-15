
package com.example.tenantmanagementsystem.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystem.R
import com.example.tenantmanagementsystem.databinding.FragmentDashboardBinding
import com.example.tenantmanagementsystem.ui.viewmodel.TenantViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel across fragments in the same activity
    private val tenantViewModel: TenantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe tenant count
        tenantViewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            binding.tenantCountTextView.text =
                "Tenants\n${tenants.size}"
        }

        // Observe total monthly rent
        tenantViewModel.totalRentText.observe(viewLifecycleOwner) { rentText ->
            val formattedRent = rentText.removePrefix("Total Monthly Rent: ")
            binding.totalRentTextView.text = "Total rent\n$formattedRent"
        }

        // Navigate to Add Tenant screen
        binding.addTenantButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_addTenantFragment
            )
        }

        // Navigate to View Tenants screen
        binding.viewTenantsButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_viewTenantsFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
