package com.apm.monsteraltech.ui.home.filters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ActionBarActivity
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.home.AdapterProductsHome
import com.apm.monsteraltech.ui.home.Product
import java.util.*
import kotlin.collections.ArrayList

class ShowCarActivity : ActionBarActivity() {
    private lateinit var adapterProduct: AdapterProductsHome
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productsList: ArrayList<Product?>
    private var typeOfFilter: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_electronic)
        setToolBar()
        applyFilters()

        var filter = findViewById<Button>(R.id.button_filter)

        filter.setOnClickListener {
            var intent = Intent(this@ShowCarActivity, CarFilterActivity::class.java)
            startActivity(intent)
        }
        setProdructs()
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

    fun setProdructs(){
        this.productsList = getProductList()

        val layoutManager = GridLayoutManager(this, 2)
        this.adapterProduct = AdapterProductsHome(productsList)
        productRecyclerView = findViewById(R.id.RecyclerViewProducts)
        productRecyclerView.adapter = this.adapterProduct
        productRecyclerView.layoutManager = layoutManager

        adapterProduct.setOnItemClickListener(object: AdapterProductsHome.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(
                    this@ShowCarActivity,
                    com.apm.monsteraltech.ProductDetail::class.java
                )
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position)?.productName)
                startActivity(intent)
            }
        })
    }

    private fun performSearch(query: String?) {
        val filteredlist = java.util.ArrayList<Product?>()
        for (item in productsList) {
            if (item != null) {
                if (query != null) {
                    if (item.productName.lowercase(Locale.getDefault()).contains(query.lowercase(
                            Locale.getDefault()))) {
                        filteredlist.add(item)
                    }
                }
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this@ShowCarActivity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapterProduct.filterList(filteredlist)
        }
    }

    private fun getProductList(): ArrayList<Product?> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        val productList: ArrayList<Product?> = arrayListOf()
        val category = ArrayList<String>()
        category.add("Audi")
        category.add("Bentley")
        category.add("bmw")
        category.add("chevrolet")
        category.add("Citroen")
        category.add("Dacia")
        category.add("Ford")
        category.add("Fiat")
        val productNames = arrayListOf(
            "Audi e-tron GT",
            " Audi A1",
            "ford fiesta 2000",
            "Citroen C4",
            "Fiat casero",
            "Dacia sanchez",
            "Bentley nadie va a leer esto",
            "bmw coche para ricos",
            "bmw coches para todavía más ricos",
            "Citroen run run"
        )
        // Agrega algunos productos a la lista para mockear la respuesta
        for (i in 0 until 10) {
            val productName = productNames[(0 until productNames.size).random()]
            val productPrice = (1..1000).random().toDouble()
            val product = Product(productName, "", "Owner", productPrice.toString(),
                category[(0 until category.size).random()]
            )
            productList.add(product)
        }

        return productList
    }
}