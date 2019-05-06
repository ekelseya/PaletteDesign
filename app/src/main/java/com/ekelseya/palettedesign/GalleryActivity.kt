package com.ekelseya.palettedesign

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

private var paletteArray: ArrayList<Palette>? = null

class GalleryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
    }

    fun onLoad() {
        val favFile = File(filesDir, "favorites")
        if (favFile.exists()) {
            ObjectInputStream(FileInputStream(favFile)).use { it ->
                val loadedPalettes = it.readObject()
                when (loadedPalettes) {
                    is ArrayList<*> -> Log.i("Load", "Deserialized")
                    else -> Log.i("Load", "Failed")
                }
                paletteArray = loadedPalettes as ArrayList<Palette>
            }
        }
    }
}