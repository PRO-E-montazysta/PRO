package com.example.e_montazysta.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.data.model.NotificationType
import com.example.e_montazysta.databinding.FragmentNotificationsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotificationListFragment : Fragment() {
    private val viewModel: NotificationListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentNotificationsBinding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val adapter = NotificationListAdapter( CustomClickListener{
                notification ->
            when(notification.notificationType) {
                    NotificationType.ORDER_CREATED -> findNavController().navigate( NotificationListFragmentDirections.actionNotificationListFragmentToOrderDetailFragment(notification.orderId!!))
                    NotificationType.ACCEPT_ORDER -> findNavController().navigate( NotificationListFragmentDirections.actionNotificationListFragmentToOrderDetailFragment(notification.orderId!!))
                    NotificationType.FOREMAN_ASSIGNMENT -> findNavController().navigate( NotificationListFragmentDirections.actionNotificationListFragmentToOrderDetailFragment(notification.orderId!!))
                    NotificationType.FITTER_ASSIGNMENT -> findNavController().navigate( NotificationListFragmentDirections.actionNotificationListFragmentToStageDetailFragment(notification.orderStageId!!))
                    NotificationType.TOOL_EVENT -> null
                    NotificationType.ELEMENT_EVENT -> null
                    NotificationType.AD_HOC_CREATED -> null
            }
            viewModel.readNotification(notification)
        })

        // TOOLBAR
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_readall -> {
                    if (adapter.elements.isNotEmpty()) {
                        viewModel.readNotification(adapter.elements)
                    } else {
                        Toast.makeText(context, "Brak nowych powiadomień!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    true
                } else -> {
                    Toast.makeText(context, "Błąd dzielenia przez ogórek", Toast.LENGTH_LONG).show()
                    false}
            }
        }

        // SWIPE TO REFRESH
        val mSwipeRefreshLayout = binding.swiperefresh
        mSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getNotification()
            mSwipeRefreshLayout.isRefreshing = false
        }


        // Przekazywanie obiektów do adaptera
        binding.list.adapter = adapter
        viewModel.getNotification()

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

    // Wyświetlanie progress bar
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

    // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this
        return binding.root

    }

}