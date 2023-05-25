package com.apm.monsteraltech.ui.activities.productDetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.*
import com.apm.monsteraltech.enumerados.State
import com.apm.monsteraltech.services.FavouriteService
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.apm.monsteraltech.ui.activities.user.userDetail.UserDetail
import com.bumptech.glide.Glide
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductDetail : ActionBarActivity() {

    private val serviceFactory = ServiceFactory()
    private val productService = serviceFactory.createService(ProductService::class.java)
    private val favouriteService = serviceFactory.createService(FavouriteService::class.java)
    private val userService = serviceFactory.createService(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setToolBar()

        var productId : Long = -1

        val productNameEditText = findViewById<TextView>(R.id.productTitle)
        var productName: String
        productName = ""
        val productStateEditText = findViewById<TextView>(R.id.productState)
        var productState : String
        productState = ""
        val productDescriptionEditText = findViewById<TextView>(R.id.productDescription)
        var productDescription : String
        productDescription = ""
        val productPriceEditText = findViewById<TextView>(R.id.productPrice)
        var productPrice : Double
        productPrice = -1.0
        val productOwnerButton = findViewById<TextView>(R.id.buttonOwner)
        var productOwner: UserProduct? = null
        val likeButton = findViewById<LottieAnimationView>(R.id.buttonLike)
        val buyButton = findViewById<Button>(R.id.buttonBuy)


        val productBundle = intent.getBundleExtra("bundle")

        val product = productBundle?.getSerializable("Product")
        if (product == null) {
            Toast.makeText(
                this,
                "Ha ocurrido un error inesperado",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        if (product is LikedProduct) {
            if (product.favourite) {
                likeButton.setImageResource(R.drawable.like_full)
            } else {
                likeButton.setImageResource(R.drawable.like_empty)
            }
            likeButton.setOnClickListener {
                likeAnimation(likeButton, R.raw.bandai_dokkan, product)
            }
            buyButton.setOnClickListener {
                val intent = Intent(this, BuyActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("Product", product)

                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }

            productId = product.id
            productName = product.name
            productState = product.state
            productDescription = product.description.toString()
            productPrice = product.price
            productOwnerButton.text = product.productOwner.name
            productOwner = product.productOwner


        } else if (product is Product)
        {
            likeButton.visibility = View.GONE
            buyButton.visibility = View.GONE

            productId = product.id
            productName = product.name
            productState = product.state
            productDescription = product.description.toString()
            productPrice = product.price
            productOwnerButton.text = product.productOwner?.name ?: "Desconocido"
        }


        productOwnerButton.setOnClickListener {
            // Acción para cancelar el registro
            val intent = Intent(this, UserDetail::class.java)
            val bundle = Bundle()
            bundle.putSerializable("User", productOwner)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }


        val state = productState?.let { State.valueOf(it) }
        productStateEditText.text = when(state){
            State.NEW-> this.getString(R.string.product_state, "nuevo")
            State.SEMI_NEW-> this.getString(R.string.product_state, "semi nuevo")
            State.SECOND_HAND-> this.getString(R.string.product_state, "segunda mano")
            State.UNKNOWN -> this.getString(R.string.product_state, "desconocido")
            else -> ({}).toString()
        }
        productNameEditText.text = productName
        productOwnerButton.text = productOwner?.name
        productDescriptionEditText.text = productDescription
        productPriceEditText.text = productPrice.toString() + " €"

        // Obtener las imágenes del producto desde el servidor
        CoroutineScope(Dispatchers.Main).launch {
            val productImages = GetImagesOfProducts(productId)
            if (productImages != null) {
                // Crear el adaptador del ViewPager
                val viewPaperAdapter = ViewPaperAdapter(productImages)

                // Configurar el ViewPager y los dots
                val viewPager = findViewById<ViewPager>(R.id.viewPaperImages)
                val dotsIndicator = findViewById<WormDotsIndicator>(R.id.wormDotsIndicator)
                viewPager.adapter = viewPaperAdapter
                dotsIndicator.attachTo(viewPager)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    private fun likeAnimation(imageView: LottieAnimationView,
                              animation: Int,
                              product: LikedProduct) : Boolean {

        if (!product.favourite) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
            CoroutineScope(Dispatchers.Main).launch {
                getUserDataFromDatastore().collect { userData : User ->
                    val user = userData.firebaseToken.let { userService.getUserByToken(it) }
                    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    favouriteService.makeFavourite(FavouriteRequest(user, product, currentDate))
                }
            }
        } else {
            imageView.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animator: Animator) {

                        imageView.setImageResource(R.drawable.like_empty)
                        imageView.alpha = 1f
                    }
                })
            CoroutineScope(Dispatchers.Main).launch {
                getUserDataFromDatastore().collect { userData : User ->
                    val user = userData.firebaseToken.let { userService.getUserByToken(it) }
                    favouriteService.quitFavourite(user.id, product.id)
                }
            }
        }

        product.favourite = !product.favourite
        return product.favourite
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

    private suspend fun GetImagesOfProducts(productId : Long): List<Drawable> {
        val imagenes = ArrayList<Drawable>()
        try {
            val response = productService.getProductImages(productId)
            if (response.isSuccessful) {
                val productImages = response.body()!!
                productImages.content.forEach { productImage ->
                    val image = Glide.with(this)
                        .load(productImage.content).submit().get()
                    imagenes.add(image)
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }

        if (imagenes.isEmpty()) {
            getDrawable(R.drawable.recyclerbin)?.let { imagenes.add(it) }
        }
        return imagenes
    }


    private class ViewPaperAdapter(private val imagenes: List<Drawable>) :
        PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(container.context)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            imageView.adjustViewBounds = true
            imageView.setImageDrawable(imagenes[position])
            Glide.with(container.context)
                .load(imagenes[position])
                .centerCrop()
                .into(imageView)
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun getCount(): Int {
            return imagenes.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }
    }

}
