package com.example.e_montazysta.ui.stage

//import com.example.e_montazysta.ui.order.StageDetailFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.e_montazysta.databinding.FragmentStageDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class StageDetailFragment : Fragment() {
    private val stageDetailViewModel: StageDetailViewModel by viewModel()
    private lateinit var stageDetailAdapter: StageDetailAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args: StageDetailFragmentArgs by navArgs()
        val stageId = args.stageId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStageDetailBinding = FragmentStageDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.stageDetailViewModel = stageDetailViewModel

        stageDetailViewModel.getStageDetail(stageId)
        viewPager = binding.pager

        stageDetailViewModel.stagedetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                stageDetailAdapter = StageDetailAdapter(this, it)
                viewPager.adapter  = stageDetailAdapter
                val tabLayout = binding.tabs
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> "Details"
                        1 -> "Releases"
                        else -> ""
                    }
                }.attach()
            }
        })

        val create_release = binding.createRelease
        create_release.setOnClickListener {
            val direction = StageDetailFragmentDirections.actionStageDetailFragmentToReleaseCreateFragment(stageId)
        findNavController().navigate(direction)
        }

        // Wyświetlanie błędów
        stageDetailViewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}
