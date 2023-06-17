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
import com.example.e_montazysta.data.model.Stage
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
    private lateinit var itemsReleaseFab: FloatingActionButton
    private lateinit var itemsReturnFab: FloatingActionButton

    //wlaczanie/wylaczanie widocznosci tekstu FABów
    private lateinit var itemsReleaseFabText: TextView
    private lateinit var itemsReturnFabText: TextView

    //sprawdzanie, czy FAB podległe pod główny FAB mają być widoczne
    private var subFabsVisible: Boolean? = null
    lateinit var stage: Stage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: StageDetailFragmentArgs by navArgs()
        val stageId = args.stageId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStageDetailBinding =
            FragmentStageDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.stageDetailViewModel = stageDetailViewModel

        stageDetailViewModel.getStageDetail(stageId)
        viewPager = binding.pager

        stageDetailViewModel.stagedetail.observe(viewLifecycleOwner, Observer { stage ->
            stage?.let {
                stageDetailAdapter = StageDetailAdapter(this, it)
                viewPager.adapter = stageDetailAdapter
                val tabLayout = binding.tabs
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> "Szczegóły"
                        1 -> "Wydania"
                        2 -> "Sugerowane Narzędzia"
                        3 -> "Sugerowane Elementy"
                        else -> ""
                    }
                }.attach()

                this.stage = stage

                when (stage.status) {
                    StageStatus.PICK_UP, StageStatus.RETURN -> {
                        mActionMainFab.show()
                    }

                    else -> mActionMainFab.hide()
                }


                itemsReleaseFab.setOnClickListener {
                    val direction =
                        StageDetailFragmentDirections.actionStageDetailFragmentToReleaseCreateFragment(
                            stage
                        )
                    findNavController().navigate(direction)
                }

                itemsReturnFab.setOnClickListener {
                    val direction =
                        StageDetailFragmentDirections.actionStageDetailFragmentToReturnCreateFragment(
                            stage
                        )
                    findNavController().navigate(direction)
                }

            }
        })


        //Rejestracja Parent FAB poprzez ID
        mActionMainFab = binding.actionMainFab

        //FAB - dzieci
        itemsReleaseFab = binding.addObjectsToRelease
        itemsReturnFab = binding.addObjectsToReturn

        //Tekst do FAB - dzieci
        itemsReleaseFabText = binding.elementEventFabText
        itemsReturnFabText = binding.addObjectsToReturnText

        //Wyłączamy widoczność FAB - dzieci i ich tekstów
        itemsReleaseFab.visibility = View.GONE
        itemsReleaseFabText.visibility = View.GONE
        itemsReturnFab.visibility = View.GONE
        itemsReturnFabText.visibility = View.GONE

        //Wyłączamy boolean sprawdzający widoczność deklarowany wyżej
        subFabsVisible = false

        //Funkcjonalność pokazywanie przycisków FAB - dzieci
        //po kliknięciu w FAB - parent
        mActionMainFab.setOnClickListener(View.OnClickListener {
            (if (!subFabsVisible!!) {
                when (stage.status) {
                    StageStatus.PICK_UP -> {
                        itemsReleaseFab.show()
                        itemsReleaseFabText.visibility = View.VISIBLE
                    }

                    StageStatus.RETURN -> {
                        itemsReturnFab.show()
                        itemsReturnFabText.visibility = View.VISIBLE
                    }

                    else -> {

                    }
                }

                //zmieniamy boolean subFabsVisible na true
                true
                //ponieważ pokazujemy FAB - dziecko wraz z tekstem
            } else {
                //ukrywamy FAB - dziecko wraz z tekstem
                itemsReleaseFab.hide()
                itemsReleaseFabText.visibility = View.GONE
                itemsReturnFab.hide()
                itemsReturnFabText.visibility = View.GONE

                //zmieniamy boolean subFabsVisible na false
                false
                //ponieważ ukrywamy FAB - dziecko wraz z tekstem
            }).also { subFabsVisible = it }
        })


        // Wyświetlanie błędów
        stageDetailViewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}
