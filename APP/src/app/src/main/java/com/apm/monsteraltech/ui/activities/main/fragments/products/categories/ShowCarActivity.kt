package com.apm.monsteraltech.ui.activities.main.fragments.products.categories

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.data.dto.ProductResponse
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters.CarFilterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowCarActivity : BaseProductsActivity() {

    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_car_home)
        super.recyclerViewProducts = findViewById(R.id.RecyclerViewProducts)
        super.context = this@ShowCarActivity
        setToolBar()
        applyFilters()
        lifecycleScope.launch(Dispatchers.IO) {
            setProducts()
        }


        var filter = findViewById<Button>(R.id.button_filter)

        filter.setOnClickListener {
            var intent = Intent(this@ShowCarActivity, CarFilterActivity::class.java)
            startActivity(intent)
        }
    }

    override suspend fun getSpecificProducts(userId : String, page: Number, size: Number): LikedProductResponse {
        return productService.getCarsWithFavourites(userId,page,size)
    }
}