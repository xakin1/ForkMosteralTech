package com.apm.monsteraltech.ui.activities.main.fragments.products.categories

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters.CarFilterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowCarActivity : BaseProductsActivity() {

    private var minKm: Number = 0
    private var maxKm: Number = Integer.MAX_VALUE
    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_car)
        super.recyclerViewProducts = findViewById(R.id.RecyclerViewProducts)
        super.progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        super.context = this@ShowCarActivity
        setToolBar()
        this.getFilters()
        lifecycleScope.launch(Dispatchers.IO) {
            setProducts()
        }

        val filter = findViewById<Button>(R.id.button_filter)

        filter.setOnClickListener {
            val intent = Intent(this@ShowCarActivity, CarFilterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getFilters(){
        super.getFilters()
        minKm = intent.getIntExtra("minKm",0)
        maxKm = intent.getIntExtra("maxKm",Integer.MAX_VALUE)
    }

    override suspend fun getSpecificProducts(userId : String, page: Number, size: Number): LikedProductResponse {
        return productService.getCarsWithFavourites(userId,page,size, super.minPrice, super.maxPrice, super.state, minKm, maxKm)
    }
}