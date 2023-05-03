package com.apm.monsteraltech

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


interface Searchable {
    fun onSearch(query: String?)
}

abstract class ActionBarActivity : AppCompatActivity() {
    var menu: Menu? = null
    private lateinit var toolbar:  Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_bar)

        toolbar = findViewById(R.id.my_toolbar)
        // Establecer la Toolbar como la ActionBar
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    fun setToolBar(){
        val t: Toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(t)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}