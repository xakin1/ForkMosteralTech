package com.apm.monsteraltech.ui.activities.main.fragments.products.categories

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters.ApplianceFilterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowAppliancesActivity : BaseProductsActivity() {

    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_appliances)
        super.recyclerViewProducts = findViewById(R.id.RecyclerViewProducts)
        super.context = this@ShowAppliancesActivity
        setToolBar()
        lifecycleScope.launch(Dispatchers.IO) {
            setProducts()
        }

        val filter = findViewById<Button>(R.id.button_filter)

        filter.setOnClickListener {
            val intent = Intent(this@ShowAppliancesActivity, ApplianceFilterActivity::class.java)
            startActivity(intent)
        }
    }

    override suspend fun getSpecificProducts(userId: String, page: Number, size: Number): LikedProductResponse {
        return productService.getAppliancesWithFavourites(userId,page,size, super.minPrice, super.maxPrice, super.state)
    }
}