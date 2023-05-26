package com.apm.monsteraltech.ui.activities.main.fragments.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.adapter.AdapterFilters
import com.apm.monsteraltech.data.adapter.AdapterLikedProduct
import com.apm.monsteraltech.data.dto.LikedProduct
import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.data.dto.User
import com.apm.monsteraltech.enumerados.State
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.actionBar.Searchable
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowCarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowAppliancesActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowFurnitureActivity
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.ShowHouseActivity
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*


@Suppress("DEPRECATION")
class ProductsFragment : Fragment(), Searchable {
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productsList: ArrayList<LikedProduct>
    private lateinit var adapterProduct: AdapterLikedProduct
    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)
    private val userService = serviceFactory.createService(UserService::class.java)
    private lateinit var user: User
    private var context: Context? = null
    private lateinit var progressBar: ProgressBar


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_products, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        lifecycleScope.launch(Dispatchers.IO) {
            getUserDataFromDatastore()?.collect { userData: User ->
                user = userData.firebaseToken.let { userService.getUserByToken(it) }
                withContext(Dispatchers.Main){
                    setProducts(view)
                }
            }
        }

        setFilters(view)
        return view
    }

    private fun setFilters(view: View){
        progressBar.visibility = View.VISIBLE
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
                        sendIntent.putExtra("userId", user.id)
                        startActivity(sendIntent)
                    }
                    "Electrodomésticos"-> {
                        sendIntent = Intent(context, ShowAppliancesActivity::class.java)
                        sendIntent.putExtra("userId", user.id)
                        startActivity(sendIntent)
                    }
                    "Muebles" -> {
                        sendIntent = Intent(context, ShowFurnitureActivity::class.java)
                        sendIntent.putExtra("userId", user.id)
                        startActivity(sendIntent)
                    }
                    "Casas" -> {
                        sendIntent = Intent(context, ShowHouseActivity::class.java)
                        sendIntent.putExtra("userId", user.id)
                        startActivity(sendIntent)
                    }
                    else -> {
                        //TODO: Poner algo aquí
                    }
                }
            }
        })
    }

    private suspend fun setProducts(view: View){
        this.productsList = getProductList()
        val layoutManager = LinearLayoutManager(context)
        this.adapterProduct = AdapterLikedProduct(productsList)
        productRecyclerView = view.findViewById(R.id.recycler_view_products)
        productRecyclerView.adapter = this.adapterProduct
        productRecyclerView.layoutManager = layoutManager
        var currentPage = 0;
        val pageSize: Number = 10;

        //LLamamos a la actividad producto detail
        adapterProduct.setOnItemClickListener(object: AdapterLikedProduct.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                val product = adapterProduct.getProduct(position)
                val intent = Intent(context, ProductDetail::class.java)
                val bundle = Bundle()
                bundle.putSerializable("Product", product)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
        })

        productRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Comprobar si el usuario ha llegado al final de la lista
                if (dy > 0 && !recyclerView.canScrollVertically(1)) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        // Cargar más elementos y actualizar el adaptador
                        try{
                            currentPage ++
                            progressBar.visibility = View.VISIBLE
                            val newData: LikedProductResponse =
                                productService.getProductsWithFavourites(user.id,currentPage, pageSize)
                            productsList.addAll(newData.content)
                            progressBar.visibility = View.GONE
                            productRecyclerView.post {
                                adapterProduct.notifyDataSetChanged()
                            }
                        } catch (e: HttpException){
                            progressBar.visibility = View.GONE
                            if (e.code() == 404) {
                                Toast.makeText(
                                    requireContext(),
                                    "No hay más productos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else {
                                Toast.makeText(
                                    requireContext(),
                                    "Ha ocurrido un error inesperado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun getFilterList(): ArrayList<Filter> {
        val filterList: ArrayList<Filter> = arrayListOf()

        filterList.add(Filter("Coches"))
        filterList.add(Filter("Casas"))
        filterList.add(Filter("Electrodomésticos"))
        filterList.add(Filter("Muebles"))
        return filterList
    }

    private suspend fun getProductList(): ArrayList<LikedProduct> {
        return withContext(lifecycleScope.coroutineContext + Dispatchers.Main) {
            val productList: ArrayList<LikedProduct> = ArrayList()

            try{
                // Obtiene las transacciones del usuario
                val userProducts: LikedProductResponse = productService.getProductsWithFavourites(user.id,0, 10)
                // Agrega las transacciones del usuario a la lista
                for (product in userProducts.content) {
                    val state = State.values().find { it.stateString == product.state } ?: State.UNKNOWN

                    val productItem = LikedProduct(
                        product.id,
                        product.name,
                        product.price,
                        product.description,
                        state.toString(),
                        product.images,
                        product.favourite,
                        product.productOwner)
                    productList.add(productItem)
                }
            } catch (e: HttpException){
                if (e.code() == 404) {
                    Toast.makeText(
                        requireContext(),
                        "No hay productos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(
                        requireContext(),
                        "Ha ocurrido un error inesperado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
                progressBar.visibility = View.GONE
                // Devuelve la lista completa
                productList
            }
    }

    override fun onSearch(query: String?) {
        val filteredlist = ArrayList<LikedProduct>()
        for (item in productsList) {
            if (query != null) {
                if (item.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
        }
        if (!filteredlist.isEmpty()) {
            adapterProduct.filterList(filteredlist)
        }
    }

    private fun getUserDataFromDatastore()  = context?.dataStore?.data?.map { preferences  ->
        User(
            id = "",
            name = preferences[stringPreferencesKey("userName")].orEmpty(),
            surname = preferences[stringPreferencesKey("userLastname")].orEmpty(),
            firebaseToken = preferences[stringPreferencesKey("userFirebaseKey")].orEmpty(),
            location = null,
            expirationDatefirebaseToken = null
        )
    }
}