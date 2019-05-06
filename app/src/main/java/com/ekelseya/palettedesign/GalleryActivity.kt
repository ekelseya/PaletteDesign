package com.ekelseya.palettedesign

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class GalleryActivity: AppCompatActivity() {
    private val primaryColor: ColorBlocks = ColorBlocks("Salmon", "#D67A7A", 214, 122, 122, 1)
    private val secondaryColor: ColorBlocks = ColorBlocks("Sunset Brick", "#BF5E3B", 191, 94, 59, 2)
    private val tertiaryColor: ColorBlocks = ColorBlocks("Raisin", "#805C5E", 128, 92, 94, 3)
    private val accentColor: ColorBlocks = ColorBlocks("Mustard", "#D6BD3D", 214, 189, 61, 4)

    private val samplePalette = Palette(primaryColor, secondaryColor, tertiaryColor, accentColor, "Sample Palette")
    private var paletteArray = arrayListOf<Palette>(samplePalette)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        onLoad()

        val gallerySpinner = findViewById<Spinner>(R.id.spinner)
        val paletteNames = mutableListOf<String>(samplePalette.pName)

        var i = 1
        while (i < paletteArray.size) {
            paletteNames.add(paletteArray[i].pName)
            i += 1
        }

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, paletteNames)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gallerySpinner.adapter = aa
        gallerySpinner.setSelection(0)

        gallerySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@GalleryActivity, "Nothing Selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val tempPalette = paletteArray[position]
                loadColor(primary_image, tempPalette.primaryBlock)
                loadColor(secondary_image, tempPalette.secondaryBlock)
                loadColor(tertiary_image, tempPalette.tertiaryBlock)
                loadColor(accent_image, tempPalette.accentBlock)
                loadNames(primary_name, tempPalette.primaryBlock)
                loadHex(primary_hex, tempPalette.primaryBlock)
                loadNames(secondary_name, tempPalette.secondaryBlock)
                loadHex(secondary_hex, tempPalette.secondaryBlock)
                loadNames(tertiary_name, tempPalette.tertiaryBlock)
                loadHex(tertiary_name, tempPalette.tertiaryBlock)
                loadNames(accent_name, tempPalette.accentBlock)
                loadHex(accent_name, tempPalette.accentBlock)
                textPaletteName.text = tempPalette.pName
            }
        }
    }

    private fun onLoad() {
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

    private fun loadColor(imageView: ImageView, colorBlocks: ColorBlocks){
        val backgroundColor = Color.rgb(colorBlocks.cRed, colorBlocks.cGreen, colorBlocks.cBlue)
        imageView.setBackgroundColor(backgroundColor)
    }
    private fun loadNames(textView: TextView, colorBlocks: ColorBlocks){
        textView.text = colorBlocks.cName
    }
    private fun loadHex(textView: TextView, colorBlocks: ColorBlocks){
        textView.text = colorBlocks.cHexRep
    }
}