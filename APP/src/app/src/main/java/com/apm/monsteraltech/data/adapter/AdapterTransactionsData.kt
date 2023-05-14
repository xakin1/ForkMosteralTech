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

            val userSellerLink = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(widget.context, UserDetail::class.java)
                    intent.putExtra("Owner", userSeller)
                    widget.context.startActivity(intent)


                }
//          Añadir esto si nos interesa cambiar el color una vez pulsado
/*                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.RED
                }*/
            }

            val userCustomerLink = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(widget.context, UserDetail::class.java)
                    intent.putExtra("Owner", userCustomer)
                    widget.context.startActivity(intent)
                }
            }

            val itemLink = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(widget.context, ProductDetail::class.java)
                    intent.putExtra("Product",item)
                    intent.putExtra("Owner", userCustomer)
                    widget.context.startActivity(intent)
                }
            }

            val dateLink = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    //TODO: FALTARIÍA VER SI ESTO TIENE QUE SER UN CLICKABLE Y SI SÍ QUE MUESTRA
                }
            }

            //  SPAN_EXCLUSIVE_EXCLUSIVE indica que el ClickableSpan solo se aplicará al rango de texto especificado y no se extenderá automáticamente si se agrega más texto alrededor del mismo.
            spannableString.setSpan(userSellerLink, transactionMessage.indexOf(userSeller), transactionMessage.indexOf(userSeller) + userSeller.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //Por si hay que cambiarle el color sería así
            //spannableString.setSpan(ForegroundColorSpan(Color.RED), transactionMessage.indexOf(userSeller), transactionMessage.indexOf(userSeller) + userSeller.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(userCustomerLink, transactionMessage.indexOf(userCustomer), transactionMessage.indexOf(userCustomer) + userCustomer.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            item.let { transactionMessage.indexOf(it) }.let {
                spannableString.setSpan(itemLink,
                    it, transactionMessage.indexOf(item) + item.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            spannableString.setSpan(dateLink, transactionMessage.indexOf(date.toString()), transactionMessage.indexOf(date.toString()) + date.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


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
