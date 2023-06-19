package com.example.e_montazysta.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.e_montazysta.databinding.FragmentOrderDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : Fragment() {
    private val orderDetailViewModel: OrderDetailViewModel by viewModel()
    private lateinit var orderDetailAdapter: OrderDetailAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val args: OrderDetailFragmentArgs by navArgs()
        val orderId = args.orderId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentOrderDetailBinding =
            FragmentOrderDetailBinding.inflate(inflater, container, false)

        orderDetailViewModel.getOrderDetail(orderId)
        viewPager = binding.pager

        orderDetailViewModel.orderdetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                orderDetailAdapter = OrderDetailAdapter(this, it)
                viewPager.adapter = orderDetailAdapter
                val tabLayout = binding.tabs
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> "Szczegóły"
                        1 -> "Etapy"
                        else -> ""
                    }
                }.attach()
            }
        })

        // Wyświetlanie błędów
        orderDetailViewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}
