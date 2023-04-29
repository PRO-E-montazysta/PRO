package com.example.e_montaysta

import Controllers.Interfaces.IAuthController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//Importy potrzebne do działania FAB w oknie Element
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.koin.android.ext.android.inject

class ElementMainActivity : AppCompatActivity() {
    //deklaracja potrzebna do działania FAB w oknie Element
    private lateinit var mElementEventFab: FloatingActionButton
    private lateinit var mElementManageFab: FloatingActionButton

    //wlaczanie/wylaczanie widocznosci tekstu FABów
    private lateinit var elementManageFabText: TextView

    //sprawdzanie, czy FAB podległe pod główny FAB mają być widoczne
    private var subFabsVisible: Boolean? = null

    private val authController: IAuthController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_element)

        //Rejestracja Parent FAB poprzez ID
        mElementEventFab = findViewById(R.id.element_event_fab)

        //FAB - dziecko
        mElementManageFab = findViewById(R.id.element_manage_fab)

        //Tekst do FAB - dziecka
        elementManageFabText = findViewById(R.id.element_manage_fab_text)

        //Wyłączamy widoczność FAB - dzieci i ich tekstów
        mElementManageFab.visibility = View.GONE
        elementManageFabText.visibility = View.GONE

        //Wyłączamy boolean sprawdzający widoczność deklarowany wyżej
        subFabsVisible = false

        //Funkcjonalność pokazywanie przycisków FAB - dzieci
        //po kliknięciu w FAB - parent
        mElementEventFab.setOnClickListener(View.OnClickListener {
            (if (!subFabsVisible!!){
                mElementManageFab.show()
                elementManageFabText.visibility = View.VISIBLE

                //zmieniamy boolean subFabsVisible na true
                //ponieważ pokazujemy FAB - dziecko wraz z tekstem
                true
            } else {
                //ukrywamy FAB - dziecko wraz z tekstem
                mElementManageFab.hide()
                elementManageFabText.visibility = View.GONE

                //zmieniamy boolean subFabsVisible na false
                //ponieważ ukrywamy FAB - dziecko wraz z tekstem
                false
            }).also { subFabsVisible = it}
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