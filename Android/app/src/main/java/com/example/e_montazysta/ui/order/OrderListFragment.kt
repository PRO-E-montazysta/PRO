package com.example.e_montazysta.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentOrdersBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialContainerTransform
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderListFragment : Fragment() {
    private val orderListViewModel: OrderListViewModel by viewModel()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = MaterialContainerTransform()
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentOrdersBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.orderListViewModel = orderListViewModel
        val adapter = OrderListAdapter( CustomClickListener{
                orderId -> findNavController().navigate( OrderListFragmentDirections.actionOrderListFragmentToOrderDetailFragment(orderId))
        })

        binding.orderList.adapter = adapter
        orderListViewModel.getOrder()

        orderListViewModel.order.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        orderListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()

        // Wyświetlanie błędów
        orderListViewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        return binding.root

    }

}
