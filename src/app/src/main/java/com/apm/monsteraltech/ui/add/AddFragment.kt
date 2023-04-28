package com.apm.monsteraltech.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apm.monsteraltech.R


class AddFragment : Fragment() {
    private lateinit var btnSubmit: Button
    private lateinit var btnClear: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        btnSubmit = view.findViewById(R.id.btn_submit)
        btnClear = view.findViewById(R.id.btn_clear)

        // Asigna el OnClickListener a los botones
        btnSubmit.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "La imagen se ha subido correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }
        btnClear.setOnClickListener{
            Toast.makeText(
                requireContext(),
            "Formulario borrado correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }

        for (i in 1..8) {
            val id = resources.getIdentifier("imageButton$i", "id", requireContext().packageName)
            val button: ImageButton = view.findViewById(id)
            val tag = button.tag as String

            // Agrega el listener a cada botón
            if (tag.startsWith("boton")) {
                button.setOnClickListener {upLoadIamge() }
            }
        }

        // Inflate the layout for this fragment
        return view

    }

    private fun upLoadIamge() {
        Toast.makeText(
            requireContext(),
            "Estamos trabajando en ello",
            Toast.LENGTH_SHORT
        ).show()

    }

}