package com.apm.monsteraltech.ui.activities.productDetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.user.userDetail.UserDetail
import com.bumptech.glide.Glide
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ProductDetail : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setToolBar()
        //TODO: Si price o productDescription son null hay que recoger los datos de la base de datos
        //TODO: Deberiamos pasarle un id de producto cuando lo tengamos
        val productName = intent.getStringExtra("Product")
        val productOwner = intent.getStringExtra("Owner")
        val productPrice = intent.getDoubleExtra("Price", 0.0).toString() + " €"
        val productDescription = intent.getStringExtra("Description")
        val productNameEditText = findViewById<TextView>(R.id.productTitle)
        val productOwnerButton = findViewById<TextView>(R.id.buttonOwner)
        val productDescriptionEditText = findViewById<TextView>(R.id.productDescription)
        val productPriceEditText = findViewById<TextView>(R.id.productPrice)
        val likeButton = findViewById<LottieAnimationView>(R.id.buttonLike)
        val buyButton = findViewById<Button>(R.id.buttonBuy)

        var liked = false
        likeButton.setOnClickListener {
            liked = likeAnimation(likeButton, R.raw.bandai_dokkan, liked)
        }


        productOwnerButton.setOnClickListener {
            // Acción para cancelar el registro
            val intent = Intent(this, UserDetail::class.java)
            intent.putExtra("Owner", productOwner)
            startActivity(intent)
        }

        buyButton.setOnClickListener {
            val intent = Intent(this, BuyActivity::class.java)
            startActivity(intent)
        }

        productNameEditText.text = productName
        productOwnerButton.text = productOwner
        productDescriptionEditText.text = productDescription
        productPriceEditText.text = productPrice

        // Obtener las imágenes del producto desde el servidor
        val imagenes = GetImagesOfProducts()

        // Crear el adaptador del ViewPager
        val viewPaperAdapter = ViewPaperAdapter(imagenes)

        // Configurar el ViewPager y los dots
        val viewPager = findViewById<ViewPager>(R.id.viewPaperImages)
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.wormDotsIndicator)
        viewPager.adapter = viewPaperAdapter
        dotsIndicator.attachTo(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    private fun likeAnimation(imageView: LottieAnimationView,
                              animation: Int,
                              like: Boolean) : Boolean {

        if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
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

        }

        return !like
    }

    private fun GetImagesOfProducts(): List<Drawable> {
        val imagenes = ArrayList<Drawable>()

        // Agregar las imágenes del producto a la lista recuperandolas del servidor
        getDrawable(R.drawable.recyclerbin)?.let { imagenes.add(it) }
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
