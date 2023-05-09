package com.apm.monsteraltech.ui.activities.main.fragments.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.adapter.AdapterFilters
import com.apm.monsteraltech.data.adapter.AdapterProductsHome
import com.apm.monsteraltech.data.dto.Product
import com.apm.monsteraltech.data.dto.ProductResponse
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.ui.activities.actionBar.Searchable
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowCarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowElectronicActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowFurnitureActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowHouseActivity
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import com.apm.monsteraltech.ui.home.categories.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


@Suppress("DEPRECATION")
class ProductsFragment : Fragment(), Searchable {
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productsList: ArrayList<Product>
    private lateinit var adapterProduct: AdapterProductsHome
    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)

    private var context: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_products, container, false)


        lifecycleScope.launch(Dispatchers.IO) {
            setProdructs(view)
        }

        setFilters(view)
        return view
    }

    fun setFilters(view: View){
        val adapterFilter = AdapterFilters(getFilterList())

        //Inicializamos la vista de filtros
        filterRecyclerView = view.findViewById(R.id.RecyclerViewFilters )
        filterRecyclerView.adapter = adapterFilter
        filterRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        adapterFilter.setOnItemClickListener(object: AdapterFilters.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                val sendIntent: Intent
                when (adapterFilter.getFilter(position).filterName) {
                    "Coches" -> {
                        sendIntent = Intent(context, ShowCarActivity::class.java)
                        startActivity(sendIntent)
                    }
                    "Electrodomésticos"-> {
                        sendIntent = Intent(context, ShowElectronicActivity::class.java)
                        startActivity(sendIntent)
                    }
                    "Muebles" -> {
                        sendIntent = Intent(context, ShowFurnitureActivity::class.java)
                        startActivity(sendIntent)
                    }
                    "Casas" -> {
                        sendIntent = Intent(context, ShowHouseActivity::class.java)
                        startActivity(sendIntent)
                    }
                    else -> {
                        //TODO: Poner algo aquí
                    }
                }
            }
        })
    }

    private suspend fun setProdructs(view: View){
        this.productsList = getProductList()
        val layoutManager = LinearLayoutManager(context)
        this.adapterProduct = AdapterProductsHome(productsList)
        productRecyclerView = view.findViewById(R.id.recycler_view_products)
        productRecyclerView.adapter = this.adapterProduct
        productRecyclerView.layoutManager = layoutManager
        var currentPage = 1;
        var pageSize: Number = 10;

        adapterProduct.setOnItemClickListener(object: AdapterProductsHome.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context,
                    ProductDetail::class.java)
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position).name)
                intent.putExtra("Owner", adapterProduct.getProduct(position).owner?.name)
                intent.putExtra("Price", adapterProduct.getProduct(position).price)
                Log.d("HomeFragment", "Price: " + adapterProduct.getProduct(position).price)
                Log.d("HomeFragment", "Owner: " + adapterProduct.getProduct(position).owner)
                //intent.putExtra("Description", adapterProduct.getProduct(position)?.description)
                startActivity(intent)
            }
        })

        //LLamamos a la actividad producto detail
        adapterProduct.setOnItemClickListener(object: AdapterProductsHome.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ProductDetail::class.java)
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position).name)
                intent.putExtra("Owner", adapterProduct.getProduct(position).owner?.name)
                intent.putExtra("Price", adapterProduct.getProduct(position).price)
                //TODO: ver si ponerle la flecha para volver atrás (la documentación no lo recomienda)
                startActivity(intent)
            }
        })

        productRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Comprobar si el usuario ha llegado al final de la lista
                if (!recyclerView.canScrollVertically(1)) {
                    currentPage ++
                    lifecycleScope.launch(Dispatchers.IO) {
                        // Cargar más elementos y actualizar el adaptador
                        val newData: ProductResponse =
                            productService.getAllProducts(currentPage, pageSize)
                        productsList.addAll(newData.content)
                        productRecyclerView.post {
                            adapterProduct.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // outState.putParcelableArrayList("productList", productsList)
    }

    private fun getFilterList(): ArrayList<Filter> {
        val filterList: ArrayList<Filter> = arrayListOf()

        filterList.add(Filter("Coches"))
        filterList.add(Filter("Casas"))
        filterList.add(Filter("Electrodomésticos"))
        filterList.add(Filter("Muebles"))
        return filterList
    }

    private suspend fun getProductList(): ArrayList<Product> {
        return withContext(lifecycleScope.coroutineContext + Dispatchers.IO) {
            val productList: ArrayList<Product> = ArrayList()

            // Obtiene las transacciones del usuario
            val userProducts: ProductResponse = productService.getAllProducts(0, 10)

            // Agrega las transacciones del usuario a la lista
            for (product in userProducts.content) {
                val productItem = Product(
                    product.id,
                    product.name,
                    product.price,
                    product.description,
                    product.state)
                productList.add(productItem)
            }
            // Devuelve la lista completa
            productList
        }
    }

    override fun onSearch(query: String?) {
        val filteredlist = ArrayList<Product>()
        for (item in productsList) {
            if (query != null) {
                if (item.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapterProduct.filterList(filteredlist)
        }
    }
}