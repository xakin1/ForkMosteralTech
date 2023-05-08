package com.apm.monsteraltech.ui.home.filters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ActionBarActivity
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.Product
import com.apm.monsteraltech.data.dto.ProductResponse
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.ui.home.AdapterProductsHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ShowHouseActivity : BaseProductsActivity() {
    private var typeOfFilter: String? = null
    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_house_home)
        super.recyclerViewProducts = findViewById(R.id.RecyclerViewProducts)
        super.context = this@ShowHouseActivity
        setToolBar()
        applyFilters()

        var filter = findViewById<Button>(R.id.button_filter)

        filter.setOnClickListener {
            var intent = Intent(this@ShowHouseActivity, HouseFilterActivity::class.java)
            startActivity(intent)
        }
    }

    override suspend fun getSpecificProducts(page: Number, size: Number): ProductResponse {
        return productService.getAllHouses(page,size)
    }
}