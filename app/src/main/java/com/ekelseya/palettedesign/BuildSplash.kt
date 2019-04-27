package com.ekelseya.palettedesign

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView


class BuildSplash : AppCompatActivity() {

    private val PREFS_BLOCKS = "prefs_blocks"
    private val KEY_BLOCKS_LIST = "color_list"

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

        val primaryColor = findViewById<ImageView>(R.id.build_color_1)
        primaryColor.setOnClickListener {
            colorCount = 1
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "primary color intent sent")
            startActivity(intent)
        }

        val primarySplashLabel = findViewById<TextView>(R.id.color_label_1)
        val primaryName = findViewById<TextView>(R.id.color_name_1)
        val primaryHex = findViewById<TextView>(R.id.text_hex1)
        val secondarySplashLabel = findViewById<TextView>(R.id.color_label_2)

        if (colorPosition == 1) {
            primarySplashLabel.visibility = View.INVISIBLE
            secondarySplashLabel.visibility = View.VISIBLE

            newColor = Color.rgb(primaryBlock.cRed,
                primaryBlock.cGreen, primaryBlock.cBlue)
            primaryColor.setBackgroundResource(0)
            primaryColor.setBackgroundColor(newColor)
            textBrightness(primaryName, newColor)
            textBrightness(primaryHex, newColor)
            primaryName.text = primaryBlock.cName
            primaryHex.text = primaryBlock.cHexRep
            primaryName.visibility = View.VISIBLE
            primaryHex.visibility = View.VISIBLE
        }

        val secondaryColor = findViewById<ImageView>(R.id.build_color_2)
        secondaryColor.setOnClickListener {
            colorCount = 2
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "secondary color intent sent")
            startActivity(intent)
        }

        val secondaryName = findViewById<TextView>(R.id.color_name_2)
        val secondaryHex = findViewById<TextView>(R.id.text_hex2)
        val tertiarySplashLabel = findViewById<TextView>(R.id.color_label_3)

        if (colorPosition == 2){
            primarySplashLabel.visibility = View.INVISIBLE
            secondarySplashLabel.visibility = View.INVISIBLE
            tertiarySplashLabel.visibility = View.VISIBLE

            newColor = Color.rgb(primaryBlock.cRed,
                primaryBlock.cGreen, primaryBlock.cBlue)
            primaryColor.setBackgroundResource(0)
            primaryColor.setBackgroundColor(newColor)
            textBrightness(primaryName, newColor)
            textBrightness(primaryHex, newColor)
            primaryName.text = primaryBlock.cName
            primaryHex.text = primaryBlock.cHexRep
            primaryName.visibility = View.VISIBLE
            primaryHex.visibility = View.VISIBLE

            newColor = Color.rgb(secondaryBlock.cRed,
                secondaryBlock.cGreen, secondaryBlock.cBlue)
            secondaryColor.setBackgroundResource(0)
            secondaryColor.setBackgroundColor(newColor)
            textBrightness(secondaryName, newColor)
            textBrightness(secondaryHex, newColor)
            secondaryName.text = secondaryBlock.cName
            secondaryHex.text = secondaryBlock.cHexRep
            secondaryName.visibility = View.VISIBLE
            secondaryHex.visibility = View.VISIBLE
        }

        val tertiaryColor = findViewById<ImageView>(R.id.build_color_3)
        tertiaryColor.setOnClickListener {
            colorCount = 3
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "tertiary color intent sent")
            startActivity(intent)
        }

        val tertiaryName = findViewById<TextView>(R.id.color_name_3)
        val tertiaryHex = findViewById<TextView>(R.id.text_hex3)
        val accentSplashLabel = findViewById<TextView>(R.id.color_label_4)

        if (colorPosition == 3){
            primarySplashLabel.visibility = View.INVISIBLE
            secondarySplashLabel.visibility = View.INVISIBLE
            tertiarySplashLabel.visibility = View.INVISIBLE
            accentSplashLabel.visibility = View.VISIBLE

            newColor = Color.rgb(primaryBlock.cRed,
                primaryBlock.cGreen, primaryBlock.cBlue)
            primaryColor.setBackgroundResource(0)
            primaryColor.setBackgroundColor(newColor)
            textBrightness(primaryName, newColor)
            textBrightness(primaryHex, newColor)
            primaryName.text = primaryBlock.cName
            primaryHex.text = primaryBlock.cHexRep
            primaryName.visibility = View.VISIBLE
            primaryHex.visibility = View.VISIBLE

            newColor = Color.rgb(secondaryBlock.cRed,
                secondaryBlock.cGreen, secondaryBlock.cBlue)
            secondaryColor.setBackgroundResource(0)
            secondaryColor.setBackgroundColor(newColor)
            textBrightness(secondaryName, newColor)
            textBrightness(secondaryHex, newColor)
            secondaryName.text = secondaryBlock.cName
            secondaryHex.text = secondaryBlock.cHexRep
            secondaryName.visibility = View.VISIBLE
            secondaryHex.visibility = View.VISIBLE

            newColor = Color.rgb(tertiaryBlock.cRed,
                tertiaryBlock.cGreen, tertiaryBlock.cBlue)
            tertiaryColor.setBackgroundResource(0)
            tertiaryColor.setBackgroundColor(newColor)
            textBrightness(tertiaryName, newColor)
            textBrightness(tertiaryHex, newColor)
            tertiaryName.text = tertiaryBlock.cName
            tertiaryHex.text = tertiaryBlock.cHexRep
            tertiaryName.visibility = View.VISIBLE
            tertiaryHex.visibility = View.VISIBLE
        }

        val accentColor = findViewById<ImageView>(R.id.build_color_4)
        accentColor.setOnClickListener {
            colorCount = 4
            val intent = Intent(this, ColorBuild::class.java)
            intent.putExtra("position", colorCount)
            Log.i("color", "accent color intent sent")
            startActivity(intent)
        }

        val accentName = findViewById<TextView>(R.id.color_name_4)
        val accentHex = findViewById<TextView>(R.id.text_hex4)

        if (colorPosition == 4){
            primarySplashLabel.visibility = View.INVISIBLE
            secondarySplashLabel.visibility = View.INVISIBLE
            tertiarySplashLabel.visibility = View.INVISIBLE
            accentSplashLabel.visibility = View.INVISIBLE

            newColor = Color.rgb(primaryBlock.cRed,
                primaryBlock.cGreen, primaryBlock.cBlue)
            primaryColor.setBackgroundResource(0)
            primaryColor.setBackgroundColor(newColor)
            textBrightness(primaryName, newColor)
            textBrightness(primaryHex, newColor)
            primaryName.text = primaryBlock.cName
            primaryHex.text = primaryBlock.cHexRep
            primaryName.visibility = View.VISIBLE
            primaryHex.visibility = View.VISIBLE

            newColor = Color.rgb(secondaryBlock.cRed,
                secondaryBlock.cGreen, secondaryBlock.cBlue)
            secondaryColor.setBackgroundResource(0)
            secondaryColor.setBackgroundColor(newColor)
            textBrightness(secondaryName, newColor)
            textBrightness(secondaryHex, newColor)
            secondaryName.text = secondaryBlock.cName
            secondaryHex.text = secondaryBlock.cHexRep
            secondaryName.visibility = View.VISIBLE
            secondaryHex.visibility = View.VISIBLE

            newColor = Color.rgb(tertiaryBlock.cRed,
                tertiaryBlock.cGreen, tertiaryBlock.cBlue)
            tertiaryColor.setBackgroundResource(0)
            tertiaryColor.setBackgroundColor(newColor)
            textBrightness(tertiaryName, newColor)
            textBrightness(tertiaryHex, newColor)
            tertiaryName.text = tertiaryBlock.cName
            tertiaryHex.text = tertiaryBlock.cHexRep
            tertiaryName.visibility = View.VISIBLE
            tertiaryHex.visibility = View.VISIBLE

            newColor = Color.rgb(accentBlock.cRed,
                accentBlock.cGreen, accentBlock.cBlue)
            accentColor.setBackgroundResource(0)
            accentColor.setBackgroundColor(newColor)
            textBrightness(accentName, newColor)
            textBrightness(accentHex, newColor)
            accentName.text = accentBlock.cName
            accentHex.text = accentBlock.cHexRep
            accentName.visibility = View.VISIBLE
            accentHex.visibility = View.VISIBLE
        }

        //TODO: Add Palette Name prompt: should become visible when all blocks are initialized
        //TODO: Add save button: should become visible when all blocks are initialized.
        //TODO: Save as map of ColorBlocks: Palette name as key?
        //TODO: or create Palette class with name and notes, save as map with date/time as key
    }

    private fun isColorDark(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    private fun textBrightness(textView: TextView, color: Int){
        if (isColorDark(color)) textView.setTextColor(Color.WHITE)
    }

    override fun onStop() {
        super.onStop()
        val savedList = StringBuilder()
        if (::primaryBlock.isInitialized) {
            savedList.append(primaryBlock.cName)
            savedList.append(",")
            savedList.append(primaryBlock.cHexRep)
            savedList.append(",")
            savedList.append(primaryBlock.cRed.toString())
            savedList.append(",")
            savedList.append(primaryBlock.cGreen.toString())
            savedList.append(",")
            savedList.append(primaryBlock.cBlue.toString())
            savedList.append(",")
            savedList.append(primaryBlock.cPos.toString())
            savedList.append(",")
        }
        if (::secondaryBlock.isInitialized) {
            savedList.append(secondaryBlock.cName)
            savedList.append(",")
            savedList.append(secondaryBlock.cHexRep)
            savedList.append(",")
            savedList.append(secondaryBlock.cRed.toString())
            savedList.append(",")
            savedList.append(secondaryBlock.cGreen.toString())
            savedList.append(",")
            savedList.append(secondaryBlock.cBlue.toString())
            savedList.append(",")
            savedList.append(secondaryBlock.cPos.toString())
            savedList.append(",")
        }
        if (::tertiaryBlock.isInitialized) {
            savedList.append(tertiaryBlock.cName)
            savedList.append(",")
            savedList.append(tertiaryBlock.cHexRep)
            savedList.append(",")
            savedList.append(tertiaryBlock.cRed.toString())
            savedList.append(",")
            savedList.append(tertiaryBlock.cGreen.toString())
            savedList.append(",")
            savedList.append(tertiaryBlock.cBlue.toString())
            savedList.append(",")
            savedList.append(tertiaryBlock.cPos.toString())
            savedList.append(",")
        }
        if (::accentBlock.isInitialized) {
            savedList.append(accentBlock.cName)
            savedList.append(",")
            savedList.append(accentBlock.cHexRep)
            savedList.append(",")
            savedList.append(accentBlock.cRed.toString())
            savedList.append(",")
            savedList.append(accentBlock.cGreen.toString())
            savedList.append(",")
            savedList.append(accentBlock.cBlue.toString())
            savedList.append(",")
            savedList.append(accentBlock.cPos.toString())
            savedList.append(",")
        }
        getSharedPreferences(PREFS_BLOCKS, Context.MODE_PRIVATE).edit().putString(KEY_BLOCKS_LIST, savedList.toString()).apply()
    }
}