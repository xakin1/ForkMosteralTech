package com.apm.monsteraltech.ui.activities.productDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity

class BuyActivity : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)
        setToolBar()
    }
}