package com.example.e_montazysta.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.FragmentDashboardBinding
import com.example.e_montazysta.ui.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModel()



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

        viewModel.getCurrentUser()
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.currentUser = it
                binding.profilePicture.load(it.profilePhotoUrl ?: "https://i.imgflip.com/3u04h5.jpg?a468072"){
                    transformations(CircleCropTransformation())
                }
            }
        })

        // TOOLBAR

        binding.toolbar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.notifications -> {
                    val direction = DashboardFragmentDirections.actionNavigationDashboardToNotificationListFragment()
                    findNavController().navigate(direction)
                    true
                }
                else -> {
                    Toast.makeText(context, "Błąd dzielenia przez ogórek", Toast.LENGTH_LONG).show()
                    false}

            }
        }





        // KAFELKI

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
        // Observe the error message LiveData
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
