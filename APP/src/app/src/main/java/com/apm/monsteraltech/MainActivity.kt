package com.apm.monsteraltech

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import com.apm.monsteraltech.databinding.MainActivityBinding
import com.apm.monsteraltech.ui.add.AddFragment
import com.apm.monsteraltech.ui.fav.FavFragment
import com.apm.monsteraltech.ui.home.HomeFragment
import com.apm.monsteraltech.ui.profile.ProfileFragment


class MainActivity : ActionBarActivity(){
    private lateinit var binding: MainActivityBinding
    private var searchableFragment: Searchable? = null
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Para la primera vez que lo carga necesitamos esto
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }



        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(fragment = HomeFragment())
                }
                R.id.fav -> {
                    replaceFragment(fragment = FavFragment())
                }
                R.id.add -> {
                    replaceFragment(fragment = AddFragment())
                }
                R.id.profile -> {
                    replaceFragment(fragment = ProfileFragment())
                }
                else -> {
                    //TODO: Poner algo aquí
                }
            }
            true // Devuelve true si el elemento ha sido seleccionado correctamente, o false si no ha sido seleccionado
        }
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)

        // Establecer la Toolbar como la ActionBar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        this.menu = menu

        // Obtener una referencia al elemento del menú
        val searchItem = menu?.findItem(R.id.action_search)

        // Obtener una referencia al SearchView a través del elemento del menú
        val searchView = searchItem?.actionView as SearchView

        // Configurar el comportamiento del SearchView
        searchView.queryHint = "Buscar productos..."
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Lógica cuando se envía la búsqueda
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO: IMPLEMENTAR BIEN ESTO
                performSearch(newText)
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)

        searchItem.isVisible = true

        return true
    }

    private fun performSearch(query: String?) {
        searchableFragment?.onSearch(query)
    }



    private fun replaceFragment(fragment : Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        //Recuperamos el nombre del fragmento para después buscarlo y usarlo para no tener que
        //cargar los datos de cada vez
        val fragmentName = fragment.javaClass.simpleName
        currentFragment = supportFragmentManager.findFragmentByTag(fragmentName)

        if (currentFragment == null) {
            fragmentTransaction.add(R.id.frame_layout, fragment, fragmentName)
        } else {
            fragmentTransaction.show(currentFragment!!)
        }

        //De todos los fragmentos ocultas todos menos el currentFragment
        supportFragmentManager.fragments
            .filter { it != currentFragment }
            .forEach { fragmentTransaction.hide(it) }

        fragmentTransaction.commit()

        // Mostrar u ocultar el SearchView según el fragmento
        when (fragment) {
            is HomeFragment ->{
                this.currentFragment = fragment
                menu?.findItem(R.id.action_search)?.isVisible = true
            }
            else -> {
                menu?.findItem(R.id.action_search)?.isVisible = false
            }
        }
    }

}