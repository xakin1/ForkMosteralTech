package com.apm.monsteraltech.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ProductDetail
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.Product
import com.apm.monsteraltech.data.dto.Transaction
import com.apm.monsteraltech.data.dto.User
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.TransactionService
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.login.dataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private lateinit var btnProducts: Button
    private lateinit var btnTransaction: Button
    private lateinit var profileLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterProductsData
    private lateinit var userBd: User
    private  var productList:  ArrayList<com.apm.monsteraltech.data.dto.Product>? = null
    private  var transactionList: ArrayList<Transaction>? = null
    private val serviceFactory = ServiceFactory()
    private val userService = serviceFactory.createService(UserService::class.java)
    private val transactionService = serviceFactory.createService(TransactionService::class.java)
    private val productService = serviceFactory.createService(ProductService::class.java)


    @SuppressLint("StringFormatMatches")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profileNameEditText = view.findViewById<TextView>(R.id.profile_name)
        val textPurchases = view.findViewById<TextView>(R.id.textPurchases)
        val textSales = view.findViewById<TextView>(R.id.textSales)

        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                getUserDataFromDatastore()?.collect { userData : User ->
                        profileNameEditText.text = userData.name + " " + userData.surname
                        userBd = userData.firebaseToken?.let { userService.getUserByToken(it) }!!
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
            showProductList()
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

        //LLamamos a la actividad producto detail
        this.adapterProduct.setOnItemClickListener(object: AdapterProductsData.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                recyclerView.getChildAt(position)
                val intent = Intent(requireContext(), ProductDetail::class.java)
                intent.putExtra("Product", adapterProduct.getProduct(position).name)
                intent.putExtra("Owner", adapterProduct.getProduct(position).owner?.name)
                intent.putExtra("Price", adapterProduct.getProduct(position).price)
                startActivity(intent)
            }
        })

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


    private suspend fun showProductList() {
        productList = productList ?: getProductList().await()

        this.adapterProduct = AdapterProductsData(productList!!)
        recyclerView.adapter = adapterProduct
    }

    private suspend fun showTransactionList() {
        transactionList = transactionList ?: getTransactionList().await()
        recyclerView.adapter = AdapterTransactionData(transactionList!!)
    }

    private fun getTransactionList(): Deferred<ArrayList<Transaction>> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        // Agrega algunas transacciones a la lista para mockear la respuesta

        return lifecycleScope.async(Dispatchers.IO) {
            val transactionList: ArrayList<Transaction> = ArrayList()

            // Obtiene las transacciones del usuario
            val userTransaction: List<Transaction> =transactionService.getAllTransactions(0, 10, userBd.id )

            // Agrega las transacciones del usuario a la lista
            for (transaction in userTransaction) {
                val transactionItem = transaction.product.description.let {
                    if (it != null) {
                        Transaction(
                            transaction.id,
                            transaction.date,
                            transaction.price,
                            transaction.state,
                            transaction.product,
                            transaction.seller,
                            transaction.buyer,
                            it,
                            transaction.date.toString()
                        )
                    }
                } as Transaction
                transactionList.add(transactionItem)
            }
            // Devuelve la lista completa
            transactionList
        }
    }

    private fun getProductList(): Deferred<ArrayList<Product>> {
        // Agrega algunas transacciones a la lista para mockear la respuesta

        return lifecycleScope.async(Dispatchers.IO) {
            val productList: ArrayList<Product> = ArrayList()

            // Obtiene las transacciones del usuario
            val userProducts: List<Product> =
                userBd.let { productService.getAllProductsOfUser(it.id,0, 10) }

            // Agrega las transacciones del usuario a la lista
            for (product in userProducts) {
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
