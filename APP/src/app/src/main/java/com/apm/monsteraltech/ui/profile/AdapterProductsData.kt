package com.apm.monsteraltech.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R

class AdapterProductsData(private val productList: ArrayList<Product>): RecyclerView.Adapter<AdapterProductsData.ViewHolder>() {
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
        private val textOwner: TextView = itemView.findViewById(R.id.product_owner)
        private val textPrice: TextView = itemView.findViewById(R.id.product_price)

        fun setData(product: Product) {
            textProductName.text = product.productName
            //TODO: Cargar imagenes de los productos aquí
            textOwner.text = product.owner
            // Si hicieramos "$${product.price}" tendríamos una vulnerabilidad de inyección de código
            textPrice.text = itemView.context.getString(R.string.product_price, product.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_profile_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(productList[position])

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    fun getProduct(position: Int): Product {
        return productList[position]
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}