package com.apm.monsteraltech.ui.activities.productDetail


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.*
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.TransactionService
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.apm.monsteraltech.ui.activities.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


class BuyActivity : ActionBarActivity() {

    private val serviceFactory = ServiceFactory()
    private val transactionService = serviceFactory.createService(TransactionService::class.java)
    private val userService = serviceFactory.createService(UserService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)
        setToolBar()

        val productBundle = intent.getBundleExtra("bundle")

        val product =  productBundle?.getSerializable("Product") as LikedProduct
        var buyerUser : User? = null

        if (product == null) {
         Toast.makeText(
             this,
             "Ha ocurrido un error inesperado",
             Toast.LENGTH_SHORT
         ).show()
         finish()
        }

        val buyerText = findViewById<TextView>(R.id.text_comprador)
        CoroutineScope(Dispatchers.Main).launch {
            getUserDataFromDatastore().collect { userData: User ->
                buyerUser = userService.getUserByToken(userData.firebaseToken)

                buyerText.text = "Comprador: " + userData.name + " " + userData.surname
            }
        }
        val sellerText = findViewById<TextView>(R.id.text_vendedor)
        sellerText.text = "Vendedor: " + (product.productOwner?.name ?: "Desconodico") + " " + (product.productOwner?.surname
            ?: "Desconodico")
        val productText = findViewById<TextView>(R.id.text_producto)
        productText.text = "Producto: " + product.name
        val priceText = findViewById<TextView>(R.id.text_precio)
        priceText.text = "Precio: " + product.price.toString() + " €"
        val buyButton = findViewById<TextView>(R.id.btn_comprar)
        val cancelButton = findViewById<TextView>(R.id.btn_cancelar)



        buyButton.setOnClickListener {
            val fecha = Date()

            val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault())
            val fechaFormateada = formato.format(fecha)
            val transaction : LikedTransaction? = buyerUser?.let { it1 ->
                LikedTransaction(
                    date = fechaFormateada,
                    product = product,
                    seller = product.productOwner,
                    buyer = it1
                )
            }

            CoroutineScope(Dispatchers.Main).launch {
                if (transaction != null) {
                    val response = transactionService.addTransaction(transaction)
                    if(response.isSuccessful){
                        Toast.makeText(
                            this@BuyActivity,
                            "Compra realizada con éxito",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@BuyActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(
                            this@BuyActivity,
                            "Ha ocurrido un error inesperado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        cancelButton.setOnClickListener {
            Toast.makeText(
                this,
                "Compra cancelada",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }


    }

    private fun getUserDataFromDatastore()  = this.dataStore.data.map { preferences  ->
        User(
            id = "",
            name = preferences[stringPreferencesKey("userName")].orEmpty(),
            surname = preferences[stringPreferencesKey("userLastname")].orEmpty(),
            firebaseToken = preferences[stringPreferencesKey("userFirebaseKey")].orEmpty(),
            location = null,
            expirationDatefirebaseToken = null
        )
    }
}