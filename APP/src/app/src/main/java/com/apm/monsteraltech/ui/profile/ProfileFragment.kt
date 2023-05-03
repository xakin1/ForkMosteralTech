package com.apm.monsteraltech.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ProductDetail
import com.apm.monsteraltech.R

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private lateinit var btnProducts: Button
    private lateinit var btnTransactions: Button
    private lateinit var profileLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: AdapterProductsData
    private  var productList:  ArrayList<Product>? = null
    private  var transactionList: ArrayList<Transactions>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        if (savedInstanceState != null) {
            // Si no estan inicializadas
            this.productList = (savedInstanceState.getParcelableArrayList<Product>("productList")
                ?.toList() ?: getProductList()) as ArrayList<Product>
            this.transactionList = (savedInstanceState.getParcelableArrayList<Transactions>("transactionList")
                ?.toList() ?: getTransactionList()) as ArrayList<Transactions>
        }
        // Inicializa los botones
        btnProducts = view.findViewById(R.id.products_button)
        btnTransactions = view.findViewById(R.id.transacciones_button)

        // Necesitamos configurar un Layout al Recycler para que funcione
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicialmente muestra la lista de productos
        showProductList()
        btnTransactions.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
        btnProducts.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_200))

        // Crea una instancia del OnClickListener para reutilizar la misma lógica en ambos botones
        val onClickListener = View.OnClickListener { viewRecycle->
            when (viewRecycle.id) {
                R.id.products_button -> {
                    btnTransactions.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
                    btnProducts.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_200))
                    showProductList()
                }
                R.id.transacciones_button -> {
                    btnProducts.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
                    btnTransactions.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_200))
                    showTransactionList()
                }
            }
        }

        // Asigna el OnClickListener a los botones
        btnProducts.setOnClickListener(onClickListener)
        btnTransactions.setOnClickListener(onClickListener)

        //LLamamos a la actividad producto detail
        this.adapterProduct.setOnItemClickListener(object: AdapterProductsData.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                recyclerView.getChildAt(position)
                val intent = Intent(requireContext(), ProductDetail::class.java)
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position).productName)
                //TODO: ver si ponerle la flecha para volver atrás (la documentación no lo recomienda)
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
        productList?.let {
            outState.putParcelableArrayList("productList", it)
        }
        transactionList?.let {
            outState.putParcelableArrayList("transactionList", it)
        }
    }


    private fun showProductList() {
        productList = productList ?: getProductList()

        this.adapterProduct = AdapterProductsData(productList!!)
        recyclerView.adapter = adapterProduct
    }

    private fun showTransactionList() {
        transactionList = transactionList ?: getTransactionList()
        recyclerView.adapter = AdapterTransactionsData(transactionList!!)
    }

    private fun getTransactionList(): ArrayList<Transactions> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        // Agrega algunas transacciones a la lista para mockear la respuesta

        val transactionList: ArrayList<Transactions> = arrayListOf()

        for (i in 0 until 9) {
            val transaction = Transactions("Usuario $i", "Usuario ${i+1}", "ObjetoX", "xx/yy/zzzz")
            transactionList.add(transaction)
        }
        return  transactionList
    }

    private fun getProductList(): ArrayList<Product> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        val productList: ArrayList<Product> = arrayListOf()

        // Agrega algunos productos a la lista para mockear la respuesta
        for (i in 0 until 10) {
            val product = Product("Producto $i","", "Owner", "99.99")
            productList.add(product)
        }

        return productList
    }

}
