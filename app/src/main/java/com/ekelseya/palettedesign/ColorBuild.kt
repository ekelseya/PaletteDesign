package com.ekelseya.palettedesign

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log.i
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class ColorBuild : AppCompatActivity(){

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_block_build)

        var redValue = 255
        var greenValue = 255
        var blueValue = 255
        var hexValue = ""
        var colorName = ""

        var colorPosition = 0
        val info = intent.extras
        if (info != null) {
            if (info.containsKey("position")) {
                colorPosition = info.getInt("position")
            }
        }
        i("ColorBuild", colorPosition.toString())

        val colorImage = findViewById<ImageView>(R.id.build_color_block)
        val btnImage = findViewById<Button>(R.id.image_button)
        val redText = findViewById<EditText>(R.id.editRed)
        val greenText = findViewById<EditText>(R.id.editGreen)
        val blueText = findViewById<EditText>(R.id.editBlue)
        val hexText = findViewById<EditText>(R.id.editHex)
        val nameText = findViewById<EditText>(R.id.nameText)
        val btnValues = findViewById<Button>(R.id.value_button)
        val btnSetColor = findViewById<Button>(R.id.set_button)

        val redLabel = findViewById<TextView>(R.id.textRed)
        val greenLabel = findViewById<TextView>(R.id.textGreen)
        val blueLabel = findViewById<TextView>(R.id.textBlue)
        val hexLabel = findViewById<TextView>(R.id.textHex)

        fun colorSet() {
            redValue = Integer.parseInt(redText.text.toString())
            greenValue = Integer.parseInt(greenText.text.toString())
            blueValue = Integer.parseInt(blueText.text.toString())

            colorImage.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue))
        }

        fun hexTextSet() {
            hexValue = String.format("#%02X%02X%02X", redValue, greenValue, blueValue)
            hexText.setText(hexValue)
            i("hex", hexValue)
        }

        btnValues.setOnClickListener {
            btnImage.visibility = View.INVISIBLE

            redText.visibility = View.VISIBLE
            greenText.visibility = View.VISIBLE
            blueText.visibility = View.VISIBLE
            hexText.visibility = View.VISIBLE
            redLabel.visibility = View.VISIBLE
            greenLabel.visibility = View.VISIBLE
            blueLabel.visibility = View.VISIBLE
            hexLabel.visibility = View.VISIBLE
            nameText.visibility = View.VISIBLE
            btnSetColor.visibility = View.VISIBLE
        }

        redText.setOnClickListener {
            colorSet()
            hexTextSet()
            i("text", "red")
            i("text", redValue.toString())
            redText.hideKeyboard()
        }

        greenText.setOnClickListener {
            colorSet()
            hexTextSet()
            i("text", "green")
            i("text", greenValue.toString())
            greenText.hideKeyboard()
        }

        blueText.setOnClickListener {
            colorSet()
            hexTextSet()
            i("text", "blue")
            i("text", blueValue.toString())
            blueText.hideKeyboard()
        }

        hexText.setOnClickListener {
            hexValue = hexText.text.toString()
            //TODO: extract colors from hex value and load into rgb
            hexText.hideKeyboard()
        }

        nameText.setOnClickListener {
            colorName = nameText.text.toString()
            nameText.hideKeyboard()
        }

//        i("colorBlock", """colorBlock initialized${colorBlock.cName}""")*/

        btnSetColor.setOnClickListener {
            //TODO: Check if name has been set: if not set error toast message instead of intent
            //TODO: Check if object works now - doesn't
//            i("Intent", colorBlock.cName)
            val intent = Intent(this, BuildSplash::class.java)
//            intent.putExtra("colorBlock", colorBlock as Parcelable)
            intent.putExtra("position", colorPosition)
            intent.putExtra("name", colorName)
            intent.putExtra("hex", hexValue)
            intent.putExtra("red", redValue)
            intent.putExtra("green", greenValue)
            intent.putExtra("blue", blueValue)
            startActivity(intent)
//            i("Intent", """return to buildsplash${colorBlock.cName}""")
        }

        //TODO: Set intent to open camera/gallery
        btnImage.setOnClickListener {
            val intent = Intent(this, GetImage::class.java)
            intent.putExtra("position", colorPosition)
            //TODO: set extras handler for GetImage
            startActivity(intent)
        }
    }
}