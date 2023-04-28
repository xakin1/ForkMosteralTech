package com.apm.monsteraltech.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.Searchable
import com.apm.monsteraltech.ui.home.filters.*
import java.util.*


@Suppress("DEPRECATION")
class HomeFragment : Fragment(), Searchable {
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productsList: ArrayList<Product?>
    private lateinit var adapterProduct: AdapterProductsHome

    private var context: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        if (savedInstanceState != null) {
            //La no deprecada requiere API 33
            productsList = savedInstanceState.getParcelableArrayList<Product>("productList")!!
        }else{
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
                var sendIntent: Intent
                when (adapterFilter.getFilter(position).filterName) {
                    "Coches" -> {
                        sendIntent = Intent(requireContext(), ShowCarActivity::class.java)
                        startActivity(sendIntent)
                    }
                    "Electrodomésticos"-> {
                        sendIntent = Intent(requireContext(), ShowElectronicActivity::class.java)
                        startActivity(sendIntent)
                    }
                    "Muebles" -> {
                        sendIntent = Intent(requireContext(), ShowFurnitureActivity::class.java)
                        startActivity(sendIntent)
                    }
                    "Casas" -> {
                        sendIntent = Intent(requireContext(), ShowHouseActivity::class.java)
                        startActivity(sendIntent)
                    }
                    else -> {
                        //TODO: Poner algo aquí
                    }
                }
            }
        })
    }

    fun setProdructs(view: View){
        this.productsList = getProductList()
        val layoutManager = GridLayoutManager(requireContext(), 2)
        this.adapterProduct = AdapterProductsHome(productsList)
        productRecyclerView = view.findViewById(R.id.RecyclerViewProducts)
        productRecyclerView.adapter = this.adapterProduct
        productRecyclerView.layoutManager = layoutManager

        adapterProduct.setOnItemClickListener(object: AdapterProductsHome.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(requireContext(),
                    com.apm.monsteraltech.ProductDetail::class.java)
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product", adapterProduct.getProduct(position)?.productName)
                startActivity(intent)
            }
        })

        //LLamamos a la actividad producto detail
        adapterProduct.setOnItemClickListener(object: AdapterProductsHome.OnItemClickedListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireContext(), com.apm.monsteraltech.ProductDetail::class.java)
                //TODO: ver que información es necesario pasarle
                intent.putExtra("Product",adapterProduct.getProduct(position)?.productName)
                //TODO: ver si ponerle la flecha para volver atrás (la documentación no lo recomienda)
                startActivity(intent)
            }
        })
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("productList", productsList)
    }

    private fun getFilterList(): ArrayList<Filter> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo

        val filterList: ArrayList<Filter> = arrayListOf()

        filterList.add(Filter("Coches"))
        filterList.add(Filter("Casas"))
        filterList.add(Filter("Electrodomésticos"))
        filterList.add(Filter("Muebles"))
        return filterList
    }

    private fun getProductList(): ArrayList<Product?> {
        //TODO: Cargar los productos desde la base de datos o de otro recurso externo
        val productList: ArrayList<Product?> = arrayListOf()
        val category = ArrayList<String>()
        category.add("Coches")
        category.add("Casas")
        category.add("Electrodomésticos")
        category.add("Muebles")
        val productNames = arrayListOf(
            "Auriculares inalámbricos",
            "Reloj inteligente",
            "Sofá cama",
            "Camiseta de algodón",
            "Bicicleta de montaña",
            "Herramientas de jardinería",
            "Juego de sábanas de seda",
            "Cafetera de goteo",
            "Barbacoa de carbón",
            "Zapatillas deportivas"
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

    // filtramos por nombre del producto destacado
    override fun onSearch(query: String?) {
        // Aquí filtramos por los productos destacados pero pienso que habría que buscar sobre el total
        // de productos que podamos obtener

        val filteredlist = ArrayList<Product?>()
        for (item in productsList) {
            if (item != null) {
                if (query != null) {
                    if (item.productName.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                        filteredlist.add(item)
                    }
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