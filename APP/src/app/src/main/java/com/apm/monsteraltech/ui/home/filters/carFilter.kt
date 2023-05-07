package com.apm.monsteraltech.ui.home.filters

import android.os.Parcel
import android.os.Parcelable
import com.apm.monsteraltech.enumerados.State

class Filtros(var precio: Int, var estado: State? = null, var quilometer: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(State::class.java.classLoader) as State?,
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