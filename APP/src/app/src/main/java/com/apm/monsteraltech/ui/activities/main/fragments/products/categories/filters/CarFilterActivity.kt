package com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowCarActivity


class CarFilterActivity : ActionBarActivity() {
    private var state: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_filter)
        setToolBar()
        val button = findViewById<Button>(R.id.applyFilter)
        button.setOnClickListener{applyFilter()}
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_new ->
                    if (checked) {
                        state = "NEW"
                    }
                R.id.radio_semi_new ->
                    if (checked) {
                        state = "SEMI_NEW"
                    }
                R.id.radio_secondHand ->
                    if (checked) {
                        state = "SECOND_HAND"
                    }
            }
        }
    }

    fun applyFilter() {
        val editMinPrice = findViewById<EditText>(R.id.edit_precio_desde)
        val editMaxPrice = findViewById<EditText>(R.id.edit_precio_hasta)
        val editMinKm = findViewById<EditText>(R.id.edit_quilometros_desde)
        val editMaxKm = findViewById<EditText>(R.id.edit_quilometros_hasta)

        val minPrice = editMinPrice.text.toString().toIntOrNull() ?: 0
        val maxPrice = editMaxPrice.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val minKm = editMinKm.text.toString().toIntOrNull() ?: 0
        val maxKm = editMaxKm.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val intent = Intent(this, ShowCarActivity::class.java)
        intent.putExtra("minPrice", minPrice)
        intent.putExtra("maxPrice", maxPrice)
        intent.putExtra("minKm", minKm)
        intent.putExtra("maxKm", maxKm)
        intent.putExtra("state", state)
        //indica que, si la actividad de destino ya está en la pila de actividades,
        // se debe eliminar cualquier actividad que se encuentre por encima de ella y
        // se debe reanudar la actividad de destino.
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}