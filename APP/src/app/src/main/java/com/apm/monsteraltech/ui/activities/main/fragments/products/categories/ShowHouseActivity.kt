package com.apm.monsteraltech.ui.activities.main.fragments.products.categories

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.ui.activities.main.fragments.products.categories.filters.HouseFilterActivity

class ShowHouseActivity : BaseProductsActivity() {

    private var minM2: Number = 0
    private var maxM2: Number = Integer.MAX_VALUE
    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_house)
        super.recyclerViewProducts = findViewById(R.id.RecyclerViewProducts)
        super.context = this@ShowHouseActivity
        setToolBar()
        this.getFilters()

        val filter = findViewById<Button>(R.id.button_filter)

        filter.setOnClickListener {
            val intent = Intent(this@ShowHouseActivity, HouseFilterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getFilters(){
        super.getFilters()
        minM2 = intent.getIntExtra("minM2",0)
        maxM2 = intent.getIntExtra("maxM2",Integer.MAX_VALUE)
    }

    override suspend fun getSpecificProducts(userId : String,page: Number, size: Number): LikedProductResponse {
        return productService.getHousesWithFavourites(userId, page,size, super.minPrice, super.maxPrice, super.state, minM2, maxM2)
    }
}