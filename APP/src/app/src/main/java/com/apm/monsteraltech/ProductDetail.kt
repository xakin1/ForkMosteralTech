package com.apm.monsteraltech

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar

class ProductDetail : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setToolBar()
    }
}