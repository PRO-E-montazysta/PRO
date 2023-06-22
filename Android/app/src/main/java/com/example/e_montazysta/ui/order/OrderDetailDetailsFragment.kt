package com.example.e_montazysta.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.databinding.FragmentOrderDetailDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat

class OrderDetailDetailsFragment(val order: Order) : Fragment() {
    private val orderDetailViewModel: OrderDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentOrderDetailDetailsBinding =
            FragmentOrderDetailDetailsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        orderDetailViewModel.setOrderDetail(order)


        orderDetailViewModel.orderdetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.nameValue.text = it.name
                binding.priorityValue.text = it.priority.value
                binding.statusValue.text = it.status.value
                it.editedAt?.let {
                    binding.plannedStartValue.text =
                        DateFormat.getDateTimeInstance().format(it)
                }
                it.editedAt?.let {
                    binding.plannedEndValue.text = DateFormat.getDateTimeInstance().format(it)
                }
                it.client?.let { client ->
                    binding.clientIdValue.text = client.name
                }

                it.foreman?.let { foremanId ->
                    binding.foremanIdValue.text = foremanId.toString()
                    binding.foreman.setOnClickListener {
                        findNavController().navigate(
                            OrderDetailFragmentDirections.actionOrderDetailFragmentToUserDetailFragment(
                                foremanId.id
                            )
                        )
                    }
                }

                it.salesRepresentative?.let { salesRepresentativeId ->
                    binding.salesRepresentativeIdValue.text = salesRepresentativeId.toString()
                    binding.salesman.setOnClickListener {
                        findNavController().navigate(
                            OrderDetailFragmentDirections.actionOrderDetailFragmentToUserDetailFragment(
                                salesRepresentativeId.id
                            )
                        )
                    }
                }
                it.editedAt?.let {
                    binding.createdAtValue.text =
                        DateFormat.getDateTimeInstance().format(it)
                }
                it.editedAt?.let {
                    binding.editedAtValue.text = DateFormat.getDateTimeInstance().format(it)
                }
                it.plannedStart?.let {
                    binding.plannedStartValue.text = DateFormat.getDateTimeInstance().format(it)
                }

                it.plannedEnd?.let {
                    binding.plannedEndValue.text = DateFormat.getDateTimeInstance().format(it)
                }

                it.location?.let {
                    binding.locationIdValue.text = it.getListItemInfo()
                }

            }
        })

        // Wyświetlanie błędów
        orderDetailViewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}