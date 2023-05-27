package com.example.e_montazysta.ui.element

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montazysta.databinding.FragmentElementsBinding
import com.example.e_montazysta.ui.activities.ElementMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElementsListFragment : Fragment() {
    private val elementsListViewModel: ElementsListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentElementsBinding = FragmentElementsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.elementsListViewModel = elementsListViewModel

        val adapter = ElementListAdapter( CustomClickListener{
                elementId ->
                val intent = Intent(requireContext(), ElementMainActivity::class.java)
                intent.putExtra("ELEMENT_ID", elementId)
                startActivity(intent)
//                elementId -> findNavController().navigate(ElementsListFragmentDirections.actionElementsListFragmentToElementMainActivity(elementId))
        })
        binding.elementList.adapter = adapter

        elementsListViewModel.getElements()

        elementsListViewModel.elements.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        elementsListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
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
        return binding.root

    }

}
