package com.example.e_montazysta.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentOrderDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : Fragment() {
    private val orderDetailViewModel: OrderDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args: OrderDetailFragmentArgs by navArgs()
        val orderId = args.orderId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentOrderDetailBinding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.orderDetailViewModel = orderDetailViewModel

        orderDetailViewModel.getOrderDetail(orderId)

        orderDetailViewModel.orderdetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.nameValue.text = it.name
                binding.priorityValue.text = it.priority
                binding.statusValue.text = it.status
                binding.plannedStartValue.text = it.plannedStart
                binding.plannedEndValue.text = it.plannedEnd
                binding.clientIdValue.text = it.client.toString()
                binding.foremanIdValue.text = it.foreman.toString()
                binding.managerIdValue.text = it.manager.toString()
                binding.specialistIdValue.text = it.specialistId.toString()
                binding.salesRepresentativeIdValue.text = it.salesRepresentativeId.toString()
                binding.createdAtValue.text = it.createdAt
                binding.editedAtValue.text = it.editedAt
            }
        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}