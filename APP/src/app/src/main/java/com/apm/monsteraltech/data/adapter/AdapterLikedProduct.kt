package com.apm.monsteraltech.data.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.LikedProduct

class AdapterLikedProduct(private var productList: List<LikedProduct>): RecyclerView.Adapter<AdapterLikedProduct.ViewHolder>() {
    private lateinit var listener: OnItemClickedListener

    interface OnItemClickedListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickedListener){
        this.listener = listener
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textProductName: TextView = itemView.findViewById(R.id.product_name)
        private val imageProduct: ImageView = itemView.findViewById(R.id.product_image_imageview)
        private val textPrice: TextView = itemView.findViewById(R.id.product_price)
        private val textDescription: TextView = itemView.findViewById(R.id.product_description)
        private val likeButton: LottieAnimationView = itemView.findViewById(R.id.product_like_button)


        @SuppressLint("StringFormatMatches")
        fun setData(product: LikedProduct) {
            textProductName.text = product.name
            //TODO: Cargar imagenes de los productos aquí
            // Si hicieramos "$${product.price}" tendríamos una vulnerabilidad de inyección de código
            textPrice.text = itemView.context.getString(R.string.product_price, product.price)
            textDescription.text = trimText(product.description.toString())
            if (product.favourite) {
                likeButton.setImageResource(R.drawable.like_full);
            } else {
                likeButton.setImageResource(R.drawable.like_empty);
            }
            likeButton.setOnClickListener {
                likeAnimation(likeButton, R.raw.bandai_dokkan, product.favourite)
            }
        }

        private fun trimText(text: String): String{
            val maxLength = 50
            val trimmedDescription = if (text.length > maxLength) {
                text.substring(0, maxLength) + "..."
            } else {
                text
            }
            return trimmedDescription
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_products_product, parent, false)
        return ViewHolder(view)
    }

    fun filterList(filterList: List<LikedProduct>) {
        productList = filterList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        productList[position].let { holder.setData(it) }

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun getProduct(position: Int): LikedProduct {
        return productList[position]
    }


}