package com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowHouseActivity
import com.google.android.material.button.MaterialButtonToggleGroup

class HouseFilterActivity : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_filter)
        setToolBar()
        val button = findViewById<Button>(R.id.applyFilter)
        button.setOnClickListener{applyFilter()}
    }

    private fun applyFilter() {
        val editMaxPrice = findViewById<EditText>(R.id.edit_precio_desde)
        val editMinPrice = findViewById<EditText>(R.id.edit_precio_hasta)
        val segmentedButtonGroupOpciones = (findViewById<View>(R.id.segmentedButtonGroup_opciones) as MaterialButtonToggleGroup)

        //TODO: mirar si al final usamos ubicación
        val editMinM2 = findViewById<EditText>(R.id.edit_tamaño_desde)
        val editMaxM2 = findViewById<EditText>(R.id.edit_tamaño_hasta)

        val minPrice = editMaxPrice.text.toString().toIntOrNull() ?: 0
        val maxPrice = editMinPrice.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val minM2 = editMinM2.text.toString().toIntOrNull() ?: 0
        val maxM2 = editMaxM2.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val state = when (segmentedButtonGroupOpciones.checkedButtonId) {
            R.id.btn_nuevo -> "Nuevo"
            R.id.btn_seminuevo -> "Reacondicionado"
            else -> null
        }
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