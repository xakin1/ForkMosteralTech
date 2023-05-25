package com.apm.monsteraltech.ui.activities.user.userDetail


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.adapter.AdapterProductsData
import com.apm.monsteraltech.data.dto.Product
import com.apm.monsteraltech.data.dto.Transaction
import com.apm.monsteraltech.data.dto.UserProduct
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.main.fragments.profile.AdapterTransactionData
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail

class UserDetail : ActionBarActivity() {

    private lateinit var btnProducts: Button
    private lateinit var btnTransaction: Button
    private lateinit var profileLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterProductsData
    private  var productList:  ArrayList<Product>? = null
    private  var transactionList: ArrayList<Transaction>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        setToolBar()

        //TODO: Recoger un id de usuario

        val userBundle = intent.getBundleExtra("bundle")

        val user = userBundle?.getSerializable("User") as UserProduct
        if (user == null) {
            Toast.makeText(
                this,
                "Ha ocurrido un error inesperado",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        val productOwnerEditText = findViewById<TextView>(R.id.profileName)
        productOwnerEditText.text = user.name + " " + user.surname

        // Inicializa los botones
        btnProducts = findViewById(R.id.productButton)
        btnTransaction = findViewById(R.id.transactionButton)

        // Necesitamos configurar un Layout al Recycler para que funcione
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialmente muestra la lista de productos
        showProductList()
        btnTransaction.setBackgroundColor( ContextCompat.getColor(this, R.color.teal_700) )
        btnProducts.setBackgroundColor( ContextCompat.getColor(this, R.color.teal_200) )

        // Crea una instancia del OnClickListener para reutilizar la misma lógica en ambos botones
        val onClickListener = View.OnClickListener { viewRecycle->
            when (viewRecycle.id) {
                R.id.productButton -> {
                    btnTransaction.setBackgroundColor( ContextCompat.getColor(this,
                        R.color.teal_700
                    ) )
                    btnProducts.setBackgroundColor( ContextCompat.getColor(this, R.color.teal_200) )
                    showProductList()
                }
                R.id.transactionButton -> {
                    btnProducts.setBackgroundColor( ContextCompat.getColor(this, R.color.teal_700) )
                    btnTransaction.setBackgroundColor( ContextCompat.getColor(this,
                        R.color.teal_200
                    ) )
                    showTransactionList()
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
                val intent = Intent(this@UserDetail, ProductDetail::class.java)
                val product = adapterProduct.getProduct(position)
                val bundle = Bundle()
                bundle.putSerializable("Product", product)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
        })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //TODO: revisar si al que haga una transacción en alguna otra actividad esto varia
        //por lo que a lo mejor esto no renta guardarlo
        // Only save the Parcelable arrays if they have been initialized

        //Esto lp hacemos en caso de que una de las dos no este inicializada
/*        productList?.let {
            outState.putParcelableArrayList("productList", it)
        }
        transactionList?.let {
            outState.putParcelableArrayList("transactionList", it)
        }*/
    }


    private fun showProductList() {
        productList = productList ?: getProductList()

        this.adapterProduct = AdapterProductsData(productList!!)
        recyclerView.adapter = adapterProduct
    }

    private fun showTransactionList() {
        transactionList = transactionList ?: getTransactionList()
        recyclerView.adapter = AdapterTransactionData(transactionList!!)
    }

    private fun getTransactionList(): ArrayList<Transaction> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        // Agrega algunas transacciones a la lista para mockear la respuesta

        val transactionList: ArrayList<Transaction> = arrayListOf()
        return  transactionList
    }

    private fun getProductList(): ArrayList<com.apm.monsteraltech.data.dto.Product> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        val productList: ArrayList<com.apm.monsteraltech.data.dto.Product> = arrayListOf()

        return productList
    }

}