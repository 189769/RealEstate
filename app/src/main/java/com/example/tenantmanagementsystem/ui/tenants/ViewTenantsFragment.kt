package com.example.tenantmanagementsystem.ui.tenants

import com.example.tenantmanagementsystem.data.repository.TenantRepository
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
import com.example.tenantmanagementsystem.databinding.FragmentViewTenantsBinding

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!
    private var selectedTenantIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedTenantIndex = savedInstanceState?.getInt("selectedTenantIndex") ?: 0
        binding.selectTenantButton.setOnClickListener {
            val tenants = TenantRepository.tenants.toList()
            if (tenants.isEmpty()) return@setOnClickListener
            AlertDialog.Builder(requireContext())
                .setTitle("Select a tenant")
                .setItems(tenants.map { "${it.name} (${it.phone})" }.toTypedArray()) { _, index ->
                    selectedTenantIndex = index
                    binding.tenant = tenants[index]
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
        binding.callButton.setOnClickListener {
            val tenant = TenantRepository.tenants.getOrNull(selectedTenantIndex)
                ?: return@setOnClickListener
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tenant.phone, null))
            try {
                startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "No phone dialer available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val tenants = TenantRepository.tenants
        binding.newTenantGroup.visibility = if (tenants.isEmpty()) View.GONE else View.VISIBLE
        binding.selectTenantButton.visibility = if (tenants.isEmpty()) View.GONE else View.VISIBLE
        if (tenants.isEmpty()) {
            binding.tenant = null
            binding.allTenantsTextView.text = "No tenants yet. Add one from the Dashboard."
        } else {
            selectedTenantIndex = selectedTenantIndex.coerceIn(tenants.indices)
            binding.tenant = tenants[selectedTenantIndex]
            binding.allTenantsTextView.text = tenants.joinToString("\n\n") { it.summary }
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
