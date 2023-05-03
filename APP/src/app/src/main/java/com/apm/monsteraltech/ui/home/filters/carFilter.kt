package com.apm.monsteraltech.ui.home.filters

import android.os.Parcel
import android.os.Parcelable
import com.apm.monsteraltech.enumerados.CarState

class Filtros(var precio: Int, var estado: CarState? = null, var quilometer: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(CarState::class.java.classLoader) as CarState?,
        parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(precio)
        parcel.writeValue(estado)
        parcel.writeInt(quilometer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Filtros> {
        override fun createFromParcel(parcel: Parcel): Filtros {
            return Filtros(parcel)
        }

        override fun newArray(size: Int): Array<Filtros?> {
            return arrayOfNulls(size)
        }
    }
}