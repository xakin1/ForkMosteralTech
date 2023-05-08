package com.apm.monsteraltech.ui.home.filters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ActionBarActivity
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.Product
import com.apm.monsteraltech.data.dto.ProductResponse
import com.apm.monsteraltech.data.dto.User
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.home.AdapterProductsHome
import com.apm.monsteraltech.ui.login.dataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import java.util.*

abstract class BaseProductsActivity : ActionBarActivity() {
    protected lateinit var adapterProduct: AdapterProductsHome
    protected lateinit var productRecyclerView: RecyclerView
    protected lateinit var productsList: ArrayList<Product>
    protected lateinit var context: Context
    protected lateinit var recyclerViewProducts: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyFilters()
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                setProducts()
            }
        }


    }

    fun applyFilters(){
        val filtros = intent.getParcelableExtra<Filtros>("filtros")

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        this.menu = menu

        // Obtener una referencia al elemento del menú
        val searchItem = menu?.findItem(R.id.action_search)

        // Obtener una referencia al SearchView a través del elemento del menú
        val searchView = searchItem?.actionView as SearchView

        // Configurar el comportamiento del SearchView
        searchView.queryHint = "Buscar productos..."
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Lógica cuando se envía la búsqueda
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText)
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        searchItem.isVisible = true
        return true
    }

    suspend fun setProducts(){
        this.productsList = getProductList()

        val layoutManager = GridLayoutManager(this, 2)
        this.adapterProduct = AdapterProductsHome(productsList)
        productRecyclerView = recyclerViewProducts
        productRecyclerView.adapter = this.adapterProduct
        productRecyclerView.layoutManager = layoutManager

        adapterProduct.setOnItemClickListener(object: AdapterProductsHome.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(
                    context,
                    com.apm.monsteraltech.ProductDetail::class.java
                )
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position).name)
                startActivity(intent)
            }
        })
    }

    override fun performSearch(newText: String?) {
        val filteredlist = ArrayList<Product>()
        for (item in productsList) {
            if (newText != null) {
                if (item.name.lowercase(Locale.getDefault()).contains(newText.lowercase(
                        Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapterProduct.filterList(filteredlist)
        }
    }

    private suspend fun getProductList(): ArrayList<Product> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        return withContext(lifecycleScope.coroutineContext + Dispatchers.IO) {
            val productList: ArrayList<Product> = ArrayList()

            // Obtiene las transacciones del usuario
            val products: ProductResponse = getSpecificProducts(0 ,10)

            // Agrega las transacciones del usuario a la lista
            for (product in products.content) {
                val productItem = Product(
                    product.id,
                    product.name,
                    product.price,
                    product.description,
                    product.state,
                    product.images,
                    product.owner
                )
                productList.add(productItem)
            }
            // Devuelve la lista completa
            productList
        }
    }

    abstract suspend fun getSpecificProducts(page: Number, size: Number): ProductResponse
}