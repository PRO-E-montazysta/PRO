package com.example.e_montazysta.ui.activities

//Importy potrzebne do działania FAB w oknie Element
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.ActivityElementBinding
import com.example.e_montazysta.ui.element.ElementDetailViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class ElementMainActivity : AppCompatActivity() {
    lateinit var topAppBar : MaterialToolbar

    //deklaracja potrzebna do działania FAB w oknie Element
    private lateinit var mActionMainFab: FloatingActionButton
    private lateinit var mElementEventFab: FloatingActionButton
    private lateinit var mElementManageFab: FloatingActionButton

    //wlaczanie/wylaczanie widocznosci tekstu FABów
    private lateinit var elementEventFabText: TextView
    private lateinit var elementManageFabText: TextView

    //sprawdzanie, czy FAB podległe pod główny FAB mają być widoczne
    private var subFabsVisible: Boolean? = null


    //SIDOR
    //    val args: ElementMainActivityArgs by navArgs()
    //    val elementId = args.elementId
    //    private val elementDetailViewModel: ElementDetailViewModel by viewModel()

    private val elementDetailViewModel: ElementDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_element)

        topAppBar = findViewById(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Navigation Icon Clicked", Toast.LENGTH_SHORT).show()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.back -> {
                    Toast.makeText(this, "Back action clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.notifications -> {
                    Toast.makeText(this, "Notifications action clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }


        val binding: ActivityElementBinding = ActivityElementBinding.inflate(layoutInflater)
        binding.elementDetailViewModel = elementDetailViewModel
        val intent: Intent = getIntent()
        val elementId: Int = intent.getIntExtra("ELEMENT_ID", 1);
        elementDetailViewModel.getElementDetail(elementId)
        elementDetailViewModel.elementdetail.observe(this, Observer {
            it?.let {
                binding.element = it
            }
        })

        //Rejestracja Parent FAB poprzez ID
        mActionMainFab = findViewById(R.id.action_main_fab)

        //FAB - dzieci
        mElementEventFab = findViewById(R.id.element_event_fab)
        mElementManageFab = findViewById(R.id.element_manage_fab)

        //Tekst do FAB - dzieci
        elementEventFabText = findViewById(R.id.element_event_fab_text)
        elementManageFabText = findViewById(R.id.element_manage_fab_text)

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

        //Mockup zgłaszania usterki
        mElementEventFab.setOnClickListener{
            Toast.makeText(this, "Zgłoszono usterkę", Toast.LENGTH_SHORT).show()
        }

        //Mockup wydawania/zwracania elementu
        mElementManageFab.setOnClickListener {
            Toast.makeText(this, "Przeniesiono wybrany element", Toast.LENGTH_SHORT).show()
        }
    }
}
