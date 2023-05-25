package com.apm.monsteraltech.ui.activities.user.userDetail


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.profile.AdapterTransactionData
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserDetail : ActionBarActivity() {

    private lateinit var btnProducts: Button
    private lateinit var btnTransaction: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterProductsData
    private lateinit var adapterTransaction: AdapterTransactionData
    private val serviceFactory = ServiceFactory()
    private  var productList:  ArrayList<Product>? = null
    private  var transactionList: ArrayList<Transaction>? = null
    private val transactionService = serviceFactory.createService(TransactionService::class.java)
    private val productService = serviceFactory.createService(ProductService::class.java)
    private val userService = serviceFactory.createService(UserService::class.java)
    private lateinit var user: User

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        setToolBar()
        val textPurchases = findViewById<TextView>(R.id.textPurchases)
        val textSales = findViewById<TextView>(R.id.textSales)


        lifecycleScope.launch(Dispatchers.Main) {
            val bundle = intent.getBundleExtra("bundle")
            val productOwner = bundle?.getSerializable("User") as UserProduct
            user = userService.getUserById(productOwner.id)
            textPurchases.text=
                this@UserDetail.getString(R.string.purchases,
                    user.id.let { transactionService.countPurchases(it).toString() })
            textSales.text=
                this@UserDetail.getString(R.string.sales,
                    user.id.let { transactionService.countSales(it).toString() })
            val productOwnerEditText = findViewById<TextView>(R.id.profileName)
            productOwnerEditText.text = user.name + " " + user.surname
            withContext(Dispatchers.Main) {
                showProductList()
            }
        }

        // Inicializa los botones
        btnProducts = findViewById(R.id.product_button)
        btnTransaction = findViewById(R.id.transaccions_button)

        // Necesitamos configurar un Layout al Recycler para que funcione
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        btnTransaction.setBackgroundColor(ContextCompat.getColor(this@UserDetail, R.color.teal_700))
        btnProducts.setBackgroundColor(ContextCompat.getColor(this@UserDetail, R.color.teal_200))

        // Crea una instancia del OnClickListener para reutilizar la misma lógica en ambos botones
        val onClickListener = View.OnClickListener { viewRecycle->
            when (viewRecycle.id) {
                R.id.product_button -> {
                    lifecycleScope.launch(Dispatchers.IO) {

                        btnTransaction.setBackgroundColor(
                            ContextCompat.getColor(
                                this@UserDetail,
                                R.color.teal_700
                            )
                        )
                        btnProducts.setBackgroundColor(
                            ContextCompat.getColor(
                                this@UserDetail,
                                R.color.teal_200
                            )
                        )
                        showProductList()
                    }
                }
                R.id.transaccions_button -> {
                    lifecycleScope.launch(Dispatchers.IO) {

                        btnProducts.setBackgroundColor(
                            ContextCompat.getColor(
                                this@UserDetail,
                                R.color.teal_700
                            )
                        )
                        btnTransaction.setBackgroundColor(
                            ContextCompat.getColor(
                                this@UserDetail,
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
    }

    private suspend fun showProductList() {
        withContext(Dispatchers.Main) {
            var currentPage = 0
            val pageSize = 10
            if (productList.isNullOrEmpty()) {
                productList = productList ?: getProductList()
            }
            adapterProduct = AdapterProductsData(productList!!)
            recyclerView.adapter = adapterProduct


            //LLamamos a la actividad producto detail
            adapterProduct.setOnItemClickListener(object:
                AdapterProductsData.OnItemClickedListener {
                override fun onItemClick(position: Int) {
                    recyclerView.getChildAt(position)
                    val intent = Intent(this@UserDetail, ProductDetail::class.java)
                    val product = adapterProduct.getProduct(position)
                    val bundle = Bundle()
                    bundle.putSerializable("Product", product)
                    intent.putExtra("bundle", bundle)
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
                                        this@UserDetail,
                                        "No hay más productos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else {
                                    Toast.makeText(
                                        this@UserDetail,
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
                                        this@UserDetail,
                                        "No hay más transacciones disponibles",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@UserDetail,
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
                    transactionService.getAllTransactions(user.id,0, 10)
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
                        this@UserDetail,
                        "No hay transacciones disponibles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(
                        this@UserDetail,
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
                        product.productOwner
                    )
                    productList.add(productItem)
                }
                return@withContext productList
            }
            catch (e: HttpException){
                if (e.code() == 404) {
                    Toast.makeText(
                        this@UserDetail,
                        "No hay Productos disponibles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(
                        this@UserDetail,
                        "Ha ocurrido un error inesperado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            // Devuelve la lista completa
            productList
        }

    }
}