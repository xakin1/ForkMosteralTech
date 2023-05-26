package com.apm.monsteraltech.ui.activities.main.fragments.profile

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.ui.activities.productDetail.ProductDetail
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.activities.user.userDetail.UserDetail
import com.apm.monsteraltech.data.dto.Transaction

class AdapterTransactionData(
    private val transactionList: ArrayList<Transaction>
): RecyclerView.Adapter<AdapterTransactionData.ViewHolder>() {


    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        fun setData(transaction: Transaction) {
            val userSeller = transaction.seller.name
            val userCustomer = transaction.buyer.name
            val item = transaction.product.name
            val date = transaction.getDate()
            val transactionMessage = context.getString(R.string.transaction_template, userSeller, userCustomer, item, date)

            val spannableString = SpannableString(transactionMessage)

            transactionMessageTextView.text = spannableString
            // Para hacer que los enlaces sean clickables
            transactionMessageTextView.movementMethod = LinkMovementMethod.getInstance()
        }

        private val transactionMessageTextView: TextView = itemView.findViewById(R.id.transaction_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_profile_transactions, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}
