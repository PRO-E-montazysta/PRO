package com.example.e_montazysta.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val warehouses = binding.warehouses
        warehouses.setOnClickListener(null)

        val tools = binding.tools
        tools.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToToolsFragment2()
            findNavController().navigate(direction)
        }

        val releases = binding.releases
        releases.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToReleaseListFragment()
            findNavController().navigate(direction)
        }

        val orders = binding.orders
        orders.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToOrderListFragment()
            findNavController().navigate(direction)

        }

        val stages = binding.stages
        stages.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToStageListFragment()
            findNavController().navigate(direction)

        }

        val elements = binding.elements
        elements.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToElementsListFragment()
            findNavController().navigate(direction)

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
