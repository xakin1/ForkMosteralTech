package com.apm.monsteraltech

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ProductDetail : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setToolBar()


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

    private fun GetImagesOfProducts(): List<Drawable> {
        val imagenes = ArrayList<Drawable>()

        // Agregar las imágenes del producto a la lista recuperandolas del servidor
        getDrawable(R.drawable.recyclerbin)?.let { imagenes.add(it) }
        getDrawable(R.drawable.ebay_icon)?.let { imagenes.add(it) }
        getDrawable(R.drawable.wallapopicon)?.let { imagenes.add(it) }

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
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageDrawable(imagenes[position])
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