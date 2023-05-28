package com.example.e_montazysta.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montazysta.databinding.FragmentNotificationsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationListFragment : Fragment() {
    private val viewModel: NotificationListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentNotificationsBinding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val adapter = NotificationListAdapter( CustomClickListener{
//                notificationId -> findNavController().navigate( NotificationListFragmentDirections.actionNotificationListFragmentToNotificationDetailFragment(notificationId))
        })


    // Przekazywanie obiektów do adaptera
        binding.notificationList.adapter = adapter
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