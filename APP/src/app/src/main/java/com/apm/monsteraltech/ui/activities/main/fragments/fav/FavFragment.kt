package com.apm.monsteraltech.ui.activities.main.fragments.fav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.adapter.AdapterLikedProduct
import com.apm.monsteraltech.data.dto.*
import com.apm.monsteraltech.services.FavouriteService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FavFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterLikedProduct
    private lateinit var productsList: ArrayList<LikedProduct>
    private val serviceFactory = ServiceFactory()
    private val favouriteService = serviceFactory.createService(FavouriteService::class.java)
    private val userService = serviceFactory.createService(UserService::class.java)
    private lateinit var user: User


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_fav, container, false)

        // Necesitamos configurar un Layout al Recycler para que funcione
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicialmente muestra la lista de productos
        lifecycleScope.launch(Dispatchers.Main) {
            getUserDataFromDatastore().collect { userData: User ->
                user = userData.firebaseToken.let { userService.getUserByToken(it) }
                showProductList()
            }
        }


        return view
    }

    private suspend fun showProductList() {
        this.productsList = getProductList()
        this.adapterProduct = AdapterLikedProduct(productsList)
        withContext(Dispatchers.Main) {
            recyclerView.adapter = adapterProduct
        }
        //LLamamos a la actividad producto detail
        this.adapterProduct.setOnItemClickListener(object: AdapterLikedProduct.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                recyclerView.getChildAt(position)
                val intent = Intent(requireContext(), ProductDetail::class.java)
                intent.putExtra("Product", adapterProduct.getProduct(position))
                intent.putExtra("ProductName", adapterProduct.getProduct(position).name)
                intent.putExtra("Price", adapterProduct.getProduct(position).price)
                startActivity(intent)
            }
        })
    }

    private suspend fun getProductList(): ArrayList<LikedProduct> {
        return withContext(lifecycleScope.coroutineContext + Dispatchers.Main) {
            val productList: ArrayList<LikedProduct> = ArrayList()
            try{
                // Obtiene las transacciones del usuario
                val newData: FavouritesResponse =
                    favouriteService.getAllFavouriteProductsOfUser(
                        user.id,
                        0, 10
                    )
                newData.content.forEach { favourites ->
                    productList.add(favourites.product)
                }
            } catch (e: HttpException){
                if (e.code() == 404) {
                    Toast.makeText(
                        requireContext(),
                        "No hay favoritos",
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
            // Devuelve la lista completa
            productList
        }
    }

    private fun getUserDataFromDatastore()  = requireContext().dataStore.data.map { preferences  ->
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