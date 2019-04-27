package com.ekelseya.palettedesign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class ColorBlocks (val cName: String, val cHexRep: String, val cRed: Int, val cGreen: Int, val cBlue: Int, val cPos: Int) : Parcelable, Serializable {
}