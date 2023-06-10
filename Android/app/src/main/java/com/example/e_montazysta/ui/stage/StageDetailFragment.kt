package com.example.e_montazysta.ui.stage

//import com.example.e_montazysta.ui.order.StageDetailFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.e_montazysta.databinding.FragmentStageDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class StageDetailFragment : Fragment() {
    private val stageDetailViewModel: StageDetailViewModel by viewModel()
    private lateinit var stageDetailAdapter: StageDetailAdapter
    private lateinit var viewPager: ViewPager2
    //deklaracja potrzebna do działania FAB w oknie Element
    private lateinit var mActionMainFab: FloatingActionButton
    private lateinit var mElementEventFab: FloatingActionButton
    private lateinit var mElementManageFab: FloatingActionButton

    //wlaczanie/wylaczanie widocznosci tekstu FABów
    private lateinit var elementEventFabText: TextView
    private lateinit var elementManageFabText: TextView

    //sprawdzanie, czy FAB podległe pod główny FAB mają być widoczne
    private var subFabsVisible: Boolean? = null
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
                        0 -> "Szczegóły"
                        1 -> "Wydania"
                        else -> ""
                    }
                }.attach()
            }
        })


        //Rejestracja Parent FAB poprzez ID
        mActionMainFab = binding.actionMainFab

        //FAB - dzieci
        mElementEventFab = binding.addObjectsToRelease
        mElementManageFab = binding.addObjectsToReturn

        //Tekst do FAB - dzieci
        elementEventFabText = binding.elementEventFabText
        elementManageFabText = binding.addObjectsToReturnText

        //Wyłączamy widoczność FAB - dzieci i ich tekstów
        mElementEventFab.visibility = View.GONE
        elementEventFabText.visibility = View.GONE
        mElementManageFab.visibility = View.GONE
        elementManageFabText.visibility = View.GONE

        //Wyłączamy boolean sprawdzający widoczność deklarowany wyżej
        subFabsVisible = false

        //Funkcjonalność pokazywanie przycisków FAB - dzieci
        //po kliknięciu w FAB - parent
        mActionMainFab.setOnClickListener(View.OnClickListener {
            (if (!subFabsVisible!!) {
                mElementEventFab.show()
                elementEventFabText.visibility = View.VISIBLE
                mElementManageFab.show()
                elementManageFabText.visibility = View.VISIBLE

                //zmieniamy boolean subFabsVisible na true
                true
                //ponieważ pokazujemy FAB - dziecko wraz z tekstem
            } else {
                //ukrywamy FAB - dziecko wraz z tekstem
                mElementEventFab.hide()
                elementEventFabText.visibility = View.GONE
                mElementManageFab.hide()
                elementManageFabText.visibility = View.GONE

                //zmieniamy boolean subFabsVisible na false
                false
                //ponieważ ukrywamy FAB - dziecko wraz z tekstem
            }).also { subFabsVisible = it }
        })

        mElementEventFab.setOnClickListener{
            val direction = StageDetailFragmentDirections.actionStageDetailFragmentToReleaseCreateFragment(stageId)
            findNavController().navigate(direction)
        }

        //Mockup zwracania elementu
        mElementManageFab.setOnClickListener {
            Toast.makeText(requireContext(), "Ekran zwrotu", Toast.LENGTH_SHORT).show()
        }
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}
