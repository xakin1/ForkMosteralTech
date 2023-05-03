package com.apm.monsteraltech.ui.profile

import android.os.Parcel
import android.os.Parcelable
import com.apm.monsteraltech.ui.home.Product

class Transactions (val customer: String, val seller: String, val item: String, val date: String):
    Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customer)
        parcel.writeString(seller)
        parcel.writeString(item)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}