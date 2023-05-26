package com.apm.monsteraltech.ui.activities.main.fragments.products.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.adapter.AdapterLikedProduct
import com.apm.monsteraltech.data.dto.LikedProduct
import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.enumerados.State
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*

abstract class BaseProductsActivity : ActionBarActivity() {
    protected lateinit var adapterProduct: AdapterLikedProduct
    protected lateinit var productRecyclerView: RecyclerView
    protected lateinit var productsList: ArrayList<LikedProduct>
    protected lateinit var context: Context
    protected lateinit var recyclerViewProducts: RecyclerView
    protected var  minPrice : Number = 0
    protected var maxPrice : Number = Integer.MAX_VALUE
    protected var state: State? = null
    private lateinit var userId: String
    protected lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.getStringExtra("userId").toString()
        lifecycleScope.launch(Dispatchers.IO) {
            setProducts()
        }
    }



    open fun getFilters(){
        minPrice = intent.getIntExtra("minPrice",0)
        maxPrice = intent.getIntExtra("maxPrice", Integer.MAX_VALUE)
        val stateString = intent.getStringExtra("state")
        if(stateString != null)
            state = State.valueOf(stateString)
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

        withContext(Dispatchers.Main) {
            val layoutManager = LinearLayoutManager(context)
            adapterProduct = AdapterLikedProduct(productsList)
            productRecyclerView = recyclerViewProducts
            productRecyclerView.adapter = adapterProduct
            productRecyclerView.layoutManager = layoutManager
            var currentPage = 1
            val pageSize: Number = 10

            adapterProduct.setOnItemClickListener(object: AdapterLikedProduct.OnItemClickedListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(
                        context,
                        ProductDetail::class.java
                    )
                    val product = adapterProduct.getProduct(position)

                    intent.putExtra("Product", product)
                    intent.putExtra("Owner", product.productOwner.name)
                    intent.putExtra("Price", product.price)
                    intent.putExtra("Description", product.description)
                    startActivity(intent)
                }
            })

            productRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // Comprobar si el usuario ha llegado al final de la lista
                    if (dy > 0 && !recyclerView.canScrollVertically(1)) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            try {
                                currentPage ++
                                progressBar.visibility = View.VISIBLE
                                // Cargar más elementos y actualizar el adaptador
                                val newData: LikedProductResponse =
                                    getSpecificProducts(
                                        userId,
                                        currentPage,
                                        pageSize
                                    )
                                productsList.addAll(newData.content)
                                progressBar.visibility = View.GONE
                                productRecyclerView.post {
                                    adapterProduct.notifyDataSetChanged()
                                }
                            }
                            catch (e: HttpException){
                                progressBar.visibility = View.GONE
                                if (e.code() == 404) {
                                    runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            "No hay más productos disponibles",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    override fun performSearch(newText: String?) {
        val filteredlist = ArrayList<LikedProduct>()
        for (item in productsList) {
            if (newText != null) {
                if (item.name.lowercase(Locale.getDefault()).contains(newText.lowercase(
                        Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
        }
        if (!filteredlist.isEmpty()) {
            adapterProduct.filterList(filteredlist)
        }
    }

    private suspend fun getProductList(): ArrayList<LikedProduct> {
        return withContext(lifecycleScope.coroutineContext + Dispatchers.Main) {
            var productList: ArrayList<LikedProduct> = ArrayList()

            // Obtiene las transacciones del usuario
            try {
                val products: LikedProductResponse = getSpecificProducts(userId,0 ,10)

                // Agrega las transacciones del usuario a la lista
                for (product in products.content) {
                    val state = State.values().find { it.stateString == product.state } ?: State.UNKNOWN

                    val productItem = LikedProduct(
                        product.id,
                        product.name,
                        product.price,
                        product.description,
                        state.toString(),
                        product.images,
                        product.favourite,
                        product.productOwner
                    )
                    productList.add(productItem)
                }
            } catch (e: HttpException){
                if (e.code() == 404) {
                    runOnUiThread {
                        productList = ArrayList()
                        Toast.makeText(
                            applicationContext,
                            "No hay productos disponibles",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            progressBar.visibility = View.GONE
            productList
        }
    }

    abstract suspend fun getSpecificProducts(userId : String,page: Number, size: Number): LikedProductResponse
}