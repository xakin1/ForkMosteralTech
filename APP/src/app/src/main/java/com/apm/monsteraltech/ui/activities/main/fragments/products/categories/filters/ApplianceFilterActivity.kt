package com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowAppliancesActivity
import com.google.android.material.button.MaterialButtonToggleGroup


class ApplianceFilterActivity : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliance_filter)
        setToolBar()
        val button = findViewById<Button>(R.id.applyFilter)
        button.setOnClickListener{applyFilter()}
    }

    private fun applyFilter() {
        val editMinPrice = findViewById<EditText>(R.id.edit_precio_desde)
        val editMaxPrice = findViewById<EditText>(R.id.edit_precio_hasta)
        val segmentedButtonGroupOpciones = (findViewById<View>(R.id.segmentedButtonGroup_opciones) as MaterialButtonToggleGroup)

        val minPrice = editMinPrice.text.toString().toIntOrNull() ?: 0
        val maxPrice = editMaxPrice.text.toString().toIntOrNull() ?: Int.MAX_VALUE
        val state = when (segmentedButtonGroupOpciones.checkedButtonId) {
            R.id.btn_nuevo -> "Nuevo"
            R.id.btn_seminuevo -> "Reacondicionado"
            else -> null
        }
        val intent = Intent(this, ShowAppliancesActivity::class.java)
        intent.putExtra("minPrice", minPrice)
        intent.putExtra("maxPrice", maxPrice)
        intent.putExtra("state", state)
        startActivity(intent)
    }

}