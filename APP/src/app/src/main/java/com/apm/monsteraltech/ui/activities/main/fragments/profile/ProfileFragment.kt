package com.apm.monsteraltech.ui.activities.main.fragments.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.adapter.AdapterProductsData
import com.apm.monsteraltech.data.dto.*
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.TransactionService
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import com.apm.monsteraltech.ui.activities.user.detailUserProfile.DetailedProfileActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import retrofit2.HttpException


@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private lateinit var btnProducts: Button
    private lateinit var btnTransaction: Button
    private lateinit var profileLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterProductsData
    private lateinit var adapterTransaction: AdapterTransactionData
    private lateinit var userBd: User
    private  var productList:  ArrayList<Product>? = null
    private  var transactionList: ArrayList<Transaction>? = null
    private val serviceFactory = ServiceFactory()
    private lateinit var user: User
    private val userService = serviceFactory.createService(UserService::class.java)
    private val transactionService = serviceFactory.createService(TransactionService::class.java)
    private val productService = serviceFactory.createService(ProductService::class.java)


    @SuppressLint("StringFormatMatches", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profileNameEditText = view.findViewById<TextView>(R.id.profile_name)
        val textPurchases = view.findViewById<TextView>(R.id.textPurchases)
        val textSales = view.findViewById<TextView>(R.id.textSales)

        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                getUserDataFromDatastore().collect { userData : User ->
                    profileNameEditText.text = userData.name + " " + userData.surname
                    userBd = userData.firebaseToken.let { userService.getUserByToken(it) }
                    textPurchases.text=
                        context?.getString(R.string.purchases,
                            userBd.id.let { transactionService.countPurchases(it).toString() })
                    textSales.text=
                        context?.getString(R.string.sales,
                            userBd.id.let { transactionService.countSales(it).toString() })
                }

            }
        }


        if (savedInstanceState != null) {
            // Si no estan inicializadas
            //TODO: Mirar estas optimizaciones
/*            this.productList = (savedInstanceState.getParcelableArrayList<Product>("productList")
                ?.toList() ?: getProductList()) as ArrayList<Product>
            this.transactionList = (savedInstanceState.getParcelableArrayList<Transaction>("transactionList")
                ?.toList() ?: getTransactionList()) as ArrayList<Transaction>*/
        }
        // Inicializa los botones
        btnProducts = view.findViewById(R.id.products_button)
        btnTransaction = view.findViewById(R.id.transacciones_button)

        // Necesitamos configurar un Layout al Recycler para que funcione
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicialmente muestra la lista de productos
        lifecycleScope.launch(Dispatchers.IO) {
            getUserDataFromDatastore().collect { userData: User ->
                user = userData.firebaseToken.let { userService.getUserByToken(it) }
                withContext(Dispatchers.Main){
                    showProductList()
                }

            }
        }
        btnTransaction.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
        btnProducts.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_200))

        // Crea una instancia del OnClickListener para reutilizar la misma lógica en ambos botones
        val onClickListener = View.OnClickListener { viewRecycle->
            when (viewRecycle.id) {
                R.id.products_button -> {
                    lifecycleScope.launch(Dispatchers.IO) {

                        btnTransaction.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.teal_700
                            )
                        )
                        btnProducts.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.teal_200
                            )
                        )
                        showProductList()
                    }
                }
                R.id.transacciones_button -> {
                    lifecycleScope.launch(Dispatchers.IO) {

                        btnProducts.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.teal_700
                            )
                        )
                        btnTransaction.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.teal_200
                            )
                        )
                        showTransactionList()
                    }
                }
            }
        }

        // Asigna el OnClickListener a los botones
        btnProducts.setOnClickListener(onClickListener)
        btnTransaction.setOnClickListener(onClickListener)

        profileLayout = view.findViewById(R.id.profile)
        profileLayout.setOnClickListener {
            val intent = Intent(requireContext(), DetailedProfileActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //TODO: revisar si al que haga una transacción en alguna otra actividad esto varia
        //por lo que a lo mejor esto no renta guardarlo
        // Only save the Parcelable arrays if they have been initialized

        //Esto lp hacemos en caso de que una de las dos no este inicializada
        //TODO: mIRAR ESTO
/*        productList?.let {
            outState.putParcelableArrayList("productList", it)
        }*/
/*        transactionList?.let {
            outState.putParcelableArrayList("transactionList", it)
        }*/
    }


    private fun updateButtonBackground(button: Button, colorResId: Int) {
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), colorResId))
    }

    private suspend fun showProductList() {
        withContext(Dispatchers.Main) {
            var currentPage = 0
            val pageSize = 10
            if (productList.isNullOrEmpty()) {
                productList = productList ?: getProductList()
                adapterProduct = AdapterProductsData(productList!!)
                recyclerView.adapter = adapterProduct
            }

            //LLamamos a la actividad producto detail
            adapterProduct.setOnItemClickListener(object:
                AdapterProductsData.OnItemClickedListener {
                override fun onItemClick(position: Int) {
                    recyclerView.getChildAt(position)
                    val intent = Intent(requireContext(), ProductDetail::class.java)
                    val product = adapterProduct.getProduct(position)
                    intent.putExtra("Product", product.name)
                    intent.putExtra("Owner", product.owner?.name)
                    intent.putExtra("Price", product.price)
                    intent.putExtra("Description", product.description)
                    startActivity(intent)
                }
            })

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // Check if the user has reached the end of the list
                    if (dy < 0 && !recyclerView.canScrollVertically(1)) {
                        currentPage++
                        lifecycleScope.launch(Dispatchers.Main) {
                            try {
                                // Load more items and update the adapter
                                val newData: ProductResponse =
                                    productService.getAllProductsOfUser(
                                        user.id,
                                        currentPage,
                                        pageSize
                                    )
                                productList!!.addAll(newData.content)
                                adapterProduct.notifyDataSetChanged()
                            } catch (e: HttpException){
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
    }

    private suspend fun showTransactionList() {
        withContext(Dispatchers.Main) {
            var currentPage = 0
            val pageSize = 10

            // Verificar si ya se ha cargado la primera página de transacciones
            if (transactionList.isNullOrEmpty()) {
                transactionList = getTransactionList()
                adapterTransaction = AdapterTransactionData(transactionList!!)
                recyclerView.adapter = adapterTransaction
            }

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // Comprobar si el usuario ha llegado al final de la lista
                    if (dy < 0 && !recyclerView.canScrollVertically(1)) {
                        currentPage++
                        lifecycleScope.launch(Dispatchers.Main) {
                            try {
                                // Cargar más elementos y actualizar el adaptador
                                val newData: TransactionsResponse =
                                    transactionService.getAllTransactions(
                                        user.id,
                                        currentPage,
                                        pageSize
                                    )

                                // Verificar si los datos ya existen en la lista de transacciones
                                if (newData.content.none { transactionList?.contains(it) == true }) {
                                    transactionList!!.addAll(newData.content)
                                    recyclerView.post {
                                        adapterTransaction.notifyDataSetChanged()
                                    }
                                }
                            } catch (e: HttpException) {
                                if (e.code() == 404) {
                                    Toast.makeText(
                                        requireContext(),
                                        "No hay más transacciones disponibles",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
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
    }



    private suspend fun getTransactionList(): ArrayList<Transaction> {

        return withContext(lifecycleScope.coroutineContext + Dispatchers.Main) {
            val transactionList: ArrayList<Transaction> = ArrayList()
            // Obtiene las transacciones del usuario
            try{
                val userTransaction: TransactionsResponse =
                    transactionService.getAllTransactions(userBd.id,0, 10)
                // Agrega las transacciones del usuario a la lista
                for (transaction in userTransaction.content) {
                    val transactionItem =
                        Transaction(
                            transaction.id,
                            transaction.date,
                            transaction.price,
                            transaction.state,
                            transaction.location,
                            transaction.product,
                            transaction.seller,
                            transaction.buyer,
                            transaction.description,
                            transaction.name
                        )
                    transactionList.add(transactionItem)
                }
                // Devuelve la lista completa
                return@withContext transactionList
            } catch (e: HttpException){
                if (e.code() == 404) {
                        Toast.makeText(
                            requireContext(),
                            "No hay transacciones disponibles",
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
            transactionList
        }
    }

    private suspend fun getProductList(): ArrayList<Product> {
        // Agrega algunas transacciones a la lista para mockear la respuesta

        return withContext(lifecycleScope.coroutineContext + Dispatchers.Main) {
            val productList: ArrayList<Product> = ArrayList()
            try {
                // Obtiene las transacciones del usuario
                val userProducts: ProductResponse =
                    productService.getAllProductsOfUser(user.id, 0, 10)

                // Agrega las transacciones del usuario a la lista
                for (product in userProducts.content) {
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
                return@withContext productList
            }
            catch (e: HttpException){
                if (e.code() == 404) {
                    Toast.makeText(
                        requireContext(),
                        "No hay Productos disponibles",
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
