package com.apm.monsteraltech.ui.home.filters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.apm.monsteraltech.ActionBarActivity
import com.apm.monsteraltech.R
import com.google.android.material.button.MaterialButtonToggleGroup


class CarFilterActivity : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_filter)
        setToolBar()
        var button = findViewById<Button>(R.id.applyFilter)
        button.setOnClickListener{applyFilter()}
    }

    fun applyFilter() {
        val editPrecioDesde = findViewById<EditText>(R.id.edit_precio_desde)
        val editPrecioHasta = findViewById<EditText>(R.id.edit_precio_hasta)
        val editKilometrosDesde = findViewById<EditText>(R.id.edit_quilometros_desde)
        val editKilometrosHasta = findViewById<EditText>(R.id.edit_quilometros_hasta)

        val precioDesde = editPrecioDesde.text.toString().toIntOrNull() ?: 0
        val precioHasta = editPrecioHasta.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val kmDesde = editKilometrosDesde.text.toString().toIntOrNull() ?: 0
        val kmHasta = editKilometrosHasta.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val estadoCoche: String
        val checkedButtonId =
            (findViewById<View>(R.id.segmentedButtonGroup_opciones) as MaterialButtonToggleGroup).checkedButtonId
        estadoCoche = if (checkedButtonId == R.id.btn_nuevo) {
            "nuevo"
        } else if (checkedButtonId == R.id.btn_seminuevo) {
            "seminuevo"
        } else if (checkedButtonId == R.id.btn_segunda_mano) {
            "segunda mano"
        } else {
            ""
        }
        val intent = Intent(this, ShowCarActivity::class.java)
        intent.putExtra("precio_desde", precioDesde)
        intent.putExtra("precio_hasta", precioHasta)
        intent.putExtra("kilometros_desde", kmDesde)
        intent.putExtra("kilometros_hasta", kmHasta)
        intent.putExtra("estado_coche", estadoCoche)
        //indica que, si la actividad de destino ya está en la pila de actividades,
        // se debe eliminar cualquier actividad que se encuentre por encima de ella y
        // se debe reanudar la actividad de destino.
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}