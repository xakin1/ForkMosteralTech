package com.apm.monsteraltech.ui.home.filters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.apm.monsteraltech.ActionBarActivity
import com.apm.monsteraltech.R
import com.google.android.material.button.MaterialButtonToggleGroup


class ElectronicFilterActivity : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electronic_filter)
        setToolBar()
        var button = findViewById<Button>(R.id.applyFilter)
        button.setOnClickListener{applyFilter()}
    }

    private fun applyFilter() {
        val editPrecioDesde = findViewById<EditText>(R.id.edit_precio_desde)
        val editPrecioHasta = findViewById<EditText>(R.id.edit_precio_hasta)
        val segmentedButtonGroupOpciones = (findViewById<View>(R.id.segmentedButtonGroup_opciones) as MaterialButtonToggleGroup)

        val precioDesde = editPrecioDesde.text.toString().toIntOrNull() ?: 0
        val precioHasta = editPrecioHasta.text.toString().toIntOrNull() ?: Int.MAX_VALUE
        val estado = when (segmentedButtonGroupOpciones.checkedButtonId) {
            R.id.btn_nuevo -> "Nuevo"
            R.id.btn_seminuevo -> "Reacondicionado"
            else -> null
        }
        val intent = Intent(this, ShowElectronicActivity::class.java)
        intent.putExtra("precio_desde", precioDesde)
        intent.putExtra("precio_hasta", precioHasta)
        intent.putExtra("estado_coche", estado)
        startActivity(intent)
    }

}