
package com.example.tenantmanagementsystem.ui.tenants

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tenantmanagementsystem.data.model.Tenant
import com.example.tenantmanagementsystem.databinding.FragmentViewTenantsBinding
import com.example.tenantmanagementsystem.ui.viewmodel.TenantViewModel

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    private val tenantViewModel: TenantViewModel by activityViewModels()

    private var selectedTenantIndex = 0
    private var currentTenants: List<Tenant> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedTenantIndex =
            savedInstanceState?.getInt("selectedTenantIndex") ?: 0

        // Observe tenant data from the shared ViewModel
        tenantViewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            currentTenants = tenants
            updateTenantDisplay()
        }

        // Select a tenant
        binding.selectTenantButton.setOnClickListener {
            if (currentTenants.isEmpty()) return@setOnClickListener

            AlertDialog.Builder(requireContext())
                .setTitle("Select a tenant")
                .setItems(
                    currentTenants.map {
                        "${it.name} (${it.phone})"
                    }.toTypedArray()
                ) { _, index ->
                    selectedTenantIndex = index
                    binding.tenant = currentTenants[index]
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Call the selected tenant
        binding.callButton.setOnClickListener {
            val tenant = currentTenants.getOrNull(selectedTenantIndex)
                ?: return@setOnClickListener

            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.fromParts("tel", tenant.phone, null)
            )

            try {
                startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "No phone dialer available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateTenantDisplay() {
        val hasTenants = currentTenants.isNotEmpty()

        binding.newTenantGroup.visibility =
            if (hasTenants) View.VISIBLE else View.GONE

        binding.selectTenantButton.visibility =
            if (hasTenants) View.VISIBLE else View.GONE

        if (!hasTenants) {
            binding.tenant = null
            binding.allTenantsTextView.text =
                "No tenants yet. Add one from the Dashboard."
        } else {
            selectedTenantIndex =
                selectedTenantIndex.coerceIn(currentTenants.indices)

            binding.tenant = currentTenants[selectedTenantIndex]

            binding.allTenantsTextView.text =
                currentTenants.joinToString("\n\n") {
                    it.summary
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedTenantIndex", selectedTenantIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
