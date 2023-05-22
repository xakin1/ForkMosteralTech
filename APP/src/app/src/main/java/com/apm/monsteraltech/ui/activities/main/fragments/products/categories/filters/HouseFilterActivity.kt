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
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowHouseActivity

class HouseFilterActivity : ActionBarActivity() {
    private var state: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_filter)
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

    private fun applyFilter() {
        val editMaxPrice = findViewById<EditText>(R.id.edit_precio_desde)
        val editMinPrice = findViewById<EditText>(R.id.edit_precio_hasta)

        val editMinM2 = findViewById<EditText>(R.id.edit_tamaño_desde)
        val editMaxM2 = findViewById<EditText>(R.id.edit_tamaño_hasta)

        val minPrice = editMaxPrice.text.toString().toIntOrNull() ?: 0
        val maxPrice = editMinPrice.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val minM2 = editMinM2.text.toString().toIntOrNull() ?: 0
        val maxM2 = editMaxM2.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val intent = Intent(this, ShowHouseActivity::class.java)
        intent.putExtra("minPrice", minPrice)
        intent.putExtra("maxPrice", maxPrice)
        intent.putExtra("minM2", minM2)
        intent.putExtra("maxM2", maxM2)
        intent.putExtra("state", state)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}