@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.ekelseya.palettedesign

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class ColorBlocks (val cName: String, val cHexRep: String, val cRed: Int, val cGreen: Int, val cBlue: Int, val cPos: Int) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt())

    companion object : Parceler<ColorBlocks> {

        override fun ColorBlocks.write(parcel: Parcel, flags: Int) {
            parcel.writeString(cName)
            parcel.writeString(cHexRep)
            parcel.writeInt(cRed)
            parcel.writeInt(cGreen)
            parcel.writeInt(cBlue)
            parcel.writeInt(cPos)
        }

        override fun create(parcel: Parcel): ColorBlocks {
            return ColorBlocks(parcel)
        }
    }
}