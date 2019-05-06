package com.ekelseya.palettedesign

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import java.io.*

private const val PREFS_BLOCKS = "prefs_blocks"
private const val KEY_BLOCKS_LIST = "color_list"

class BuildSplash : AppCompatActivity() {
    private var paletteArray: MutableList<Palette>? = null

    private var colorPosition: Int = 0
    private var colorCount: Int = 0
    private lateinit var primaryBlock:ColorBlocks
    //TODO: Not saving blocks between activities
    private lateinit var secondaryBlock: ColorBlocks
    private lateinit var tertiaryBlock: ColorBlocks
    private lateinit var accentBlock: ColorBlocks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.build_splash)

        //Declare all views
        val primaryColor = findViewById<ImageView>(R.id.primary_image)
        val primarySplashLabel = findViewById<TextView>(R.id.color_label_1)
        val primaryName = findViewById<TextView>(R.id.primary_name)
        val primaryHex = findViewById<TextView>(R.id.primary_hex)

        val secondaryColor = findViewById<ImageView>(R.id.secondary_image)
        val secondarySplashLabel = findViewById<TextView>(R.id.color_label_2)
        val secondaryName = findViewById<TextView>(R.id.secondary_name)
        val secondaryHex = findViewById<TextView>(R.id.secondary_hex)

        val tertiaryColor = findViewById<ImageView>(R.id.tertiary_image)
        val tertiarySplashLabel = findViewById<TextView>(R.id.color_label_3)
        val tertiaryName = findViewById<TextView>(R.id.tertiary_name)
        val tertiaryHex = findViewById<TextView>(R.id.tertiary_hex)

        val accentColor = findViewById<ImageView>(R.id.accent_image)
        val accentSplashLabel = findViewById<TextView>(R.id.color_label_4)
        val accentName = findViewById<TextView>(R.id.accent_name)
        val accentHex = findViewById<TextView>(R.id.accent_hex)

        val paletteNameBox = findViewById<EditText>(R.id.editPaletteName)
        val paletteSaveButton = findViewById<Button>(R.id.paletteSaveButton)

        //Get previous colors from shared preferences
        val savedList = getSharedPreferences(PREFS_BLOCKS, Context.MODE_PRIVATE).getString(KEY_BLOCKS_LIST, null)
        if(savedList != null){
            val info = savedList.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (info.size > 1) {
                primaryBlock =
                    ColorBlocks(info[0], info[1], info[2].toInt(), info[3].toInt(), info[4].toInt(), info[5].toInt())
            }
            if (info.size > 6) {
                secondaryBlock =
                    ColorBlocks(info[6], info[7], info[8].toInt(), info[9].toInt(), info[10].toInt(), info[11].toInt())
            }
            if (info.size > 12) {
                tertiaryBlock =
                    ColorBlocks(info[12], info[13], info[14].toInt(), info[15].toInt(), info[16].toInt(), info[17].toInt())
            }
            if (info.size > 18) {
                accentBlock =
                    ColorBlocks(info[18], info[19], info[20].toInt(), info[21].toInt(), info[22].toInt(), info[23].toInt())
            }
        }

        //Declare values
        colorPosition = intent.getIntExtra("position", 0)
        val name = intent.getStringExtra("name")
        val hex = intent.getStringExtra("hex")
        val red = intent.getIntExtra("red", 0)
        val green = intent.getIntExtra("green", 0)
        val blue = intent.getIntExtra("blue", 0)
        Log.i("buildSplash", colorPosition.toString())

        when (colorPosition) {
            1 -> primaryBlock = ColorBlocks(name, hex, red, green, blue, colorPosition)
            2 -> secondaryBlock = ColorBlocks(name, hex, red, green, blue, colorPosition)
            3 -> tertiaryBlock = ColorBlocks(name, hex, red, green, blue, colorPosition)
            4 -> accentBlock = ColorBlocks(name, hex, red, green, blue, colorPosition)
        }

        var newColor: Int

        primaryColor.setOnClickListener {
            colorCount = 1
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "primary color intent sent")
            startActivity(intent)
        }

        fun primaryVisibility(){
            primarySplashLabel.visibility = View.INVISIBLE
            primaryName.visibility = View.VISIBLE
            primaryHex.visibility = View.VISIBLE
            secondarySplashLabel.visibility = View.VISIBLE
        }

        fun primaryColorSet(){
            newColor = Color.rgb(primaryBlock.cRed,
                primaryBlock.cGreen, primaryBlock.cBlue)
            primaryColor.setBackgroundResource(0)
            primaryColor.setBackgroundColor(newColor)
            textBrightness(primaryName, newColor)
            textBrightness(primaryHex, newColor)
            primaryName.text = primaryBlock.cName
            primaryHex.text = primaryBlock.cHexRep
        }

        fun secondaryVisibility(){
            secondarySplashLabel.visibility = View.INVISIBLE
            secondaryName.visibility = View.VISIBLE
            secondaryHex.visibility = View.VISIBLE
            tertiarySplashLabel.visibility = View.VISIBLE
        }

        fun secondaryColorSet(){
            newColor = Color.rgb(secondaryBlock.cRed,
                secondaryBlock.cGreen, secondaryBlock.cBlue)
            secondaryColor.setBackgroundResource(0)
            secondaryColor.setBackgroundColor(newColor)
            textBrightness(secondaryName, newColor)
            textBrightness(secondaryHex, newColor)
            secondaryName.text = secondaryBlock.cName
            secondaryHex.text = secondaryBlock.cHexRep
        }

        fun tertiaryVisibility(){
            tertiarySplashLabel.visibility = View.INVISIBLE
            tertiaryName.visibility = View.VISIBLE
            tertiaryHex.visibility = View.VISIBLE
            accentSplashLabel.visibility = View.VISIBLE
        }

        fun tertiaryColorSet(){
            newColor = Color.rgb(tertiaryBlock.cRed,
            tertiaryBlock.cGreen, tertiaryBlock.cBlue)
            tertiaryColor.setBackgroundResource(0)
            tertiaryColor.setBackgroundColor(newColor)
            textBrightness(tertiaryName, newColor)
            textBrightness(tertiaryHex, newColor)
            tertiaryName.text = tertiaryBlock.cName
            tertiaryHex.text = tertiaryBlock.cHexRep
        }

        fun accentVisibility(){
            accentSplashLabel.visibility = View.INVISIBLE
            accentName.visibility = View.VISIBLE
            accentHex.visibility = View.VISIBLE
            paletteNameBox.visibility = View.VISIBLE
            paletteSaveButton.visibility = View.VISIBLE
        }

        fun accentColorSet(){
            newColor = Color.rgb(accentBlock.cRed,
                accentBlock.cGreen, accentBlock.cBlue)
            accentColor.setBackgroundResource(0)
            accentColor.setBackgroundColor(newColor)
            textBrightness(accentName, newColor)
            textBrightness(accentHex, newColor)
            accentName.text = accentBlock.cName
            accentHex.text = accentBlock.cHexRep
        }

        if (colorPosition == 1) {
            primaryVisibility()
            primaryColorSet()
        }

        secondaryColor.setOnClickListener {
            colorCount = 2
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "secondary color intent sent")
            startActivity(intent)
        }

        if (colorPosition == 2){
            primaryVisibility()
            primaryColorSet()
            secondaryVisibility()
            secondaryColorSet()
        }

        tertiaryColor.setOnClickListener {
            colorCount = 3
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "tertiary color intent sent")
            startActivity(intent)
        }

        if (colorPosition == 3){
            primaryVisibility()
            secondaryVisibility()
            primaryColorSet()
            secondaryColorSet()
            tertiaryVisibility()
            tertiaryColorSet()
        }

        accentColor.setOnClickListener {
            colorCount = 4
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "accent color intent sent")
            startActivity(intent)
        }

        if (colorPosition == 4){
            primaryVisibility()
            primaryColorSet()
            secondaryVisibility()
            secondaryColorSet()
            tertiaryVisibility()
            tertiaryColorSet()
            accentVisibility()
            accentColorSet()
            paletteSaveButton.visibility = View.VISIBLE
            paletteNameBox.visibility = View.VISIBLE
        }

        val paletteName = paletteNameBox.text.toString()
        val palette = Palette(primaryBlock, secondaryBlock, tertiaryBlock, accentBlock, paletteName)
        onLoad()
        paletteArray!!.add(palette)

        paletteSaveButton.setOnClickListener(){
            onSave()
        }

        //TODO: or create Palette class with name and notes, save as map with date/time as key
    }

    private fun isColorDark(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    private fun textBrightness(textView: TextView, color: Int){
        if (isColorDark(color)) textView.setTextColor(Color.WHITE)
    }

    private fun preferenceStringBuilder(colorBlocks: ColorBlocks): String{
        val savedString = java.lang.StringBuilder()
        savedString.append(colorBlocks.cName)
        savedString.append(",")
        savedString.append(colorBlocks.cHexRep)
        savedString.append(",")
        savedString.append(colorBlocks.cRed.toString())
        savedString.append(",")
        savedString.append(colorBlocks.cGreen.toString())
        savedString.append(",")
        savedString.append(colorBlocks.cBlue.toString())
        savedString.append(",")
        savedString.append(colorBlocks.cPos.toString())
        savedString.append(",")

        return savedString.toString()
    }

    override fun onStop() {
        super.onStop()
        var savedList = ""
        if (::primaryBlock.isInitialized) {
            savedList = preferenceStringBuilder(primaryBlock)
        }
        if (::secondaryBlock.isInitialized) {
            savedList = preferenceStringBuilder(primaryBlock)+
                    preferenceStringBuilder(secondaryBlock)
        }
        if (::tertiaryBlock.isInitialized) {
            savedList = preferenceStringBuilder(primaryBlock) +
                    preferenceStringBuilder(secondaryBlock) +
                    preferenceStringBuilder(tertiaryBlock)
        }
        if (::accentBlock.isInitialized) {
            savedList = preferenceStringBuilder(primaryBlock) +
                    preferenceStringBuilder(secondaryBlock) +
                    preferenceStringBuilder(tertiaryBlock) +
                    preferenceStringBuilder(accentBlock)
        }
        getSharedPreferences(PREFS_BLOCKS, Context.MODE_PRIVATE).edit().putString(KEY_BLOCKS_LIST, savedList).apply()
    }
    fun onSave() {
        val favFile = File(filesDir, "favorites")
        ObjectOutputStream(FileOutputStream(favFile)).use { it -> it.writeObject(paletteArray) }

        Toast.makeText(this, "New Palette Saved!", Toast.LENGTH_SHORT).show()
    }
    fun onLoad() {
        val favFile = File(filesDir, "favorites")
        if (favFile.exists()) {
            ObjectInputStream(FileInputStream(favFile)).use { it ->
                val loadedPalettes = it.readObject()
                when (loadedPalettes) {
                    is MutableList<*> -> Log.i("Load", "Deserialized")
                    else -> Log.i("Load", "Failed")
                }
                paletteArray = loadedPalettes as MutableList<Palette>
            }
        }
    }
}