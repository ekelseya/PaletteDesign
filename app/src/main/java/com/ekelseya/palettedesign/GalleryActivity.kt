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
    private var paletteMap = mutableMapOf<String, Array<ColorBlocks>>()

    private val primaryColor: ColorBlocks = ColorBlocks("Salmon", "#D67A7A", 214, 122, 122, 1)
    private val secondaryColor: ColorBlocks = ColorBlocks("Sunset Brick", "#BF5E3B", 191, 94, 59, 2)
    private val tertiaryColor: ColorBlocks = ColorBlocks("Raisin", "#805C5E", 128, 92, 94, 3)
    private val accentColor: ColorBlocks = ColorBlocks("Mustard", "#D6BD3D", 214, 189, 61, 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        onLoad()

        val gallerySpinner = findViewById<Spinner>(R.id.spinner)
        val paletteNames = paletteMap.keys.toList()

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, paletteNames)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gallerySpinner.adapter = aa
        gallerySpinner.setSelection(0)

        gallerySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@GalleryActivity, "Nothing Selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val name = paletteNames[position]
                val tempPalette = paletteMap[name]
                loadColor(primary_image, tempPalette!![0])
                loadColor(secondary_image, tempPalette[1])
                loadColor(tertiary_image, tempPalette[2])
                loadColor(accent_image, tempPalette[3])
                loadNames(primary_name, tempPalette[0])
                loadHex(primary_hex, tempPalette[0])
                loadNames(secondary_name, tempPalette[1])
                loadHex(secondary_hex, tempPalette[1])
                loadNames(tertiary_name, tempPalette[2])
                loadHex(tertiary_hex, tempPalette[2])
                loadNames(accent_name, tempPalette[3])
                loadHex(accent_hex, tempPalette[3])
                textPaletteName.text = name
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
                paletteMap = loadedPalettes as MutableMap<String, Array<ColorBlocks>>
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