package com.example.e_montazysta.ui.element

//import com.example.e_montazysta.ui.order.ElementDetailFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.FragmentElementDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat


class ElementDetailFragment : Fragment() {
   /* lateinit var topAppBar : MaterialToolbar

    //deklaracja potrzebna do działania FAB w oknie Element
    private lateinit var mActionMainFab: FloatingActionButton
    private lateinit var mElementEventFab: FloatingActionButton
    private lateinit var mElementManageFab: FloatingActionButton

    //wlaczanie/wylaczanie widocznosci tekstu FABów
    private lateinit var elementEventFabText: TextView
    private lateinit var elementManageFabText: TextView

    //sprawdzanie, czy FAB podległe pod główny FAB mają być widoczne
    private var subFabsVisible: Boolean? = null*/

    private val elementDetailViewModel: ElementDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //super.onCreate(savedInstanceState)

       //setContentView(R.layout.activity_element)

        /*topAppBar = this(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            Toast.makeText(activity, "Navigation Icon Clicked", Toast.LENGTH_SHORT).show()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.back -> {
                    Toast.makeText(activity, "Back action clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.notifications -> {
                    Toast.makeText(activity, "Notifications action clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }*/

        val args: ElementDetailFragmentArgs by navArgs()
        val elementId = args.elementId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentElementDetailBinding = FragmentElementDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.elementDetailViewModel = elementDetailViewModel

        elementDetailViewModel.getElementDetail(elementId)

        elementDetailViewModel.elementdetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.elementNameData.text = it.name
                binding.elementCodeData.text = it.code
                binding.elementUnitData.text = it.typeOfUnit
                binding.elementQtyOfUnitData.text = it.quantityInUnit.toString()
//                binding.elementWarehouseData.text = it.elementInWarehouses.toString()
            }
        })

//        val create_release = binding.createRelease
//        create_release.setOnClickListener {
//            val direction = ElementDetailFragmentDirections.actionElementDetailFragmentToReleaseCreateFragment(elementId)
//        findNavController().navigate(direction)
//        }
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        //Rejestracja Parent FAB poprzez ID
        //mActionMainFab = findViewById(R.id.action_main_fab)

        //FAB - dzieci
        //mElementEventFab = findViewById(R.id.element_event_fab)
        //mElementManageFab = findViewById(R.id.element_manage_fab)

        //Tekst do FAB - dzieci
        //elementEventFabText = findViewById(R.id.element_event_fab_text)
        //elementManageFabText = findViewById(R.id.element_manage_fab_text)

        //Wyłączamy widoczność FAB - dzieci i ich tekstów
        //mElementEventFab.visibility = View.GONE
        //elementEventFabText.visibility = View.GONE
        //mElementManageFab.visibility = View.GONE
        //elementManageFabText.visibility = View.GONE

        //Wyłączamy boolean sprawdzający widoczność deklarowany wyżej
        //subFabsVisible = false

        //Funkcjonalność pokazywanie przycisków FAB - dzieci
        //po kliknięciu w FAB - parent
       /* mActionMainFab.setOnClickListener(View.OnClickListener {
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

        //Mockup zgłaszania usterki
        mElementEventFab.setOnClickListener{
            Toast.makeText(activity, "Zgłoszono usterkę", Toast.LENGTH_SHORT).show()
        }

        //Mockup wydawania/zwracania elementu
        mElementManageFab.setOnClickListener {
            Toast.makeText(activity, "Przeniesiono wybrany element", Toast.LENGTH_SHORT).show()
        }*/
        return binding.root
    }
}
