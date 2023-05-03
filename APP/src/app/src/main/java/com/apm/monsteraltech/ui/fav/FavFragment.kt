package com.apm.monsteraltech.ui.fav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ProductDetail
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.profile.AdapterProductsData
import com.apm.monsteraltech.ui.profile.Product

class FavFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterProductsData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_fav, container, false)

        // Necesitamos configurar un Layout al Recycler para que funcione
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicialmente muestra la lista de productos
        showProductList()

        //LLamamos a la actividad producto detail
        this.adapterProduct.setOnItemClickListener(object: AdapterProductsData.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                recyclerView.getChildAt(position)
                val intent = Intent(requireContext(), ProductDetail::class.java)
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position).productName)
                //TODO: ver si ponerle la flecha para volver atrás (la documentación no lo recomienda)
                startActivity(intent)
            }
        })

        return view
    }

    private fun showProductList() {
        this.adapterProduct = AdapterProductsData(getProductList())
        recyclerView.adapter = adapterProduct
    }

    private fun getProductList(): ArrayList<Product> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        val productList: ArrayList<Product> = arrayListOf()

        // Agrega algunos productos a la lista para mockear la respuesta
        for (i in 0 until 10) {
            val product = Product("Producto $i","", "Owner", "99.99")
            productList.add(product)
        }

        return productList
    }
}