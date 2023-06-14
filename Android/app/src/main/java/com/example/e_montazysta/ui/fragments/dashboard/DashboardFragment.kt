package com.example.e_montazysta.ui.fragments.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.FragmentDashboardBinding
import com.example.e_montazysta.ui.notification.NotificationListViewModel
import com.example.e_montazysta.ui.viewmodels.DashboardViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModel()
    private val notificationViewModel: NotificationListViewModel by viewModel()
    private var isBackPressedFromDialog = false



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    @com.google.android.material.badge.ExperimentalBadgeUtils
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
                binding.profilePicture.load(it.profilePhotoUrl ?: "https://cdn-icons-png.flaticon.com/512/149/149071.png"){
                    transformations(CircleCropTransformation())
                }
            }
        })

        // TOOLBAR

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
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

        val badgeDrawable = BadgeDrawable.create(requireContext())
        notificationViewModel.getNotification()
        notificationViewModel.notificationsNumberLiveData.observe(viewLifecycleOwner) {
            notificationNumber ->
            when(notificationNumber){
                0 -> BadgeUtils.detachBadgeDrawable(badgeDrawable, binding.toolbar, R.id.notifications)
                else -> {
                    badgeDrawable.number = notificationNumber
                    BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.toolbar, R.id.notifications)
                }
            }

        }
        // KAFELKI

        val warehouses = binding.warehouses
        warehouses.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToWarehouseListFragment()
            findNavController().navigate(direction)
        }

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

        val events = binding.events
        events.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToEventListFragment()
            findNavController().navigate(direction)
        }

        val users = binding.users
        users.setOnClickListener{
            val direction = DashboardFragmentDirections.actionNavigationDashboardToUserListFragment()
            findNavController().navigate(direction)
        }

        // Observe the error message LiveData
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }


        // EXIT
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!isBackPressedFromDialog) {
                    showExitDialog()
                } else {
                    requireActivity().onBackPressed()
                }
            }
        })
        return root
    }
    private fun showExitDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Uwaga!")
        alertDialogBuilder.setMessage("Czy na pewno chcesz opuścić aplikację?")
        alertDialogBuilder.setPositiveButton("Wyjdź") { _, _ ->
            isBackPressedFromDialog = true
            requireActivity().moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            exitProcess(1);
        }
        alertDialogBuilder.setNegativeButton("Anuluj", null)
        alertDialogBuilder.setOnDismissListener {
            isBackPressedFromDialog = false
        }
        alertDialogBuilder.create().show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
