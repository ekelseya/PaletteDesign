package com.ekelseya.palettedesign

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.color_block_build.*

class ColorBuild : AppCompatActivity() {
    //TODO: Link to ColorPicker!

    //TODO: Check for null values rgb
    //TODO: Check for out of bounds values for rgb
    //TODO: Fix focus on red values
    //TODO: Fix focus on return from image: Focus should go to Color Name (not red value)

    private var colorPosition = 0
    private var redValue = 255
    private var greenValue = 255
    private var blueValue = 255

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_block_build)
        var hexValue = ""
        var colorName = ""
        val colorImage = findViewById<ImageView>(R.id.build_color_block)
        val btnImage = findViewById<Button>(R.id.buttonImage1)
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
        val iconImage = findViewById<ImageView>(R.id.buildIcon)
        val btnPicker = findViewById<Button>(R.id.picker_button)
        val info = intent.extras
        if (info != null) {
            if (info.containsKey("position")) {
                colorPosition = info.getInt("position")
            }
            if (info.containsKey("red")) {
                redValue = info.getInt("red")
                greenValue = info.getInt("green")
                blueValue = info.getInt("blue")
                btnPicker.visibility = View.INVISIBLE
                btnImage.visibility = View.INVISIBLE
                iconImage.visibility = View.INVISIBLE
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
                colorImage.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue))
                redText.setText(redValue.toString())
                greenText.setText(greenValue.toString())
                blueText.setText(blueValue.toString())
                hexValue = String.format("#%02X%02X%02X", redValue, greenValue, blueValue)
                hexText.setText(hexValue)
            }
        }
        fun testValues(int: Int): Boolean {
            return when {
                int > 255 -> {
                    Toast.makeText(this@ColorBuild, "Error! Values must be less than 255", Toast.LENGTH_LONG).show()
                    false
                }
                int < 0 -> {
                    Toast.makeText(this@ColorBuild, "Error! Values must be less than 255", Toast.LENGTH_LONG).show()
                    false
                }
                else -> true
            }
        }
        fun colorSet() {
            iconImage.visibility = View.INVISIBLE
            redValue = Integer.parseInt(redText.text.toString())
            greenValue = Integer.parseInt(greenText.text.toString())
            blueValue = Integer.parseInt(blueText.text.toString())
            colorImage.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue))
        }

        fun hexTextSet() {
            hexValue = String.format("#%02X%02X%02X", redValue, greenValue, blueValue)
            hexText.setText(hexValue)
        }
        btnValues.setOnClickListener {
            btnImage.visibility = View.INVISIBLE
            iconImage.visibility = View.INVISIBLE
            btnPicker.visibility = View.INVISIBLE
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
            redText.hideKeyboard()
        }
        greenText.setOnClickListener {
            colorSet()
            hexTextSet()
            greenText.hideKeyboard()
        }
        blueText.setOnClickListener {
            colorSet()
            hexTextSet()
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
        btnSetColor.setOnClickListener {
            //TODO: Check if name has been set: if not set error toast message instead of intent

            val intent = Intent(this, BuildSplash::class.java)
            intent.putExtra("position", colorPosition)
            intent.putExtra("name", colorName)
            intent.putExtra("hex", hexValue)
            intent.putExtra("red", redValue)
            intent.putExtra("green", greenValue)
            intent.putExtra("blue", blueValue)
            startActivity(intent)
        }
        btnImage.setOnClickListener {
            val intent = Intent(this, GetImage::class.java)
            intent.putExtra("position", colorPosition)
            startActivity(intent)
        }
        btnPicker.setOnClickListener {
            launchColorPicker()

            val newColor = Color.rgb(redValue, greenValue, blueValue)

            colorImage.setBackgroundColor(newColor)
        }
    }
    private fun launchColorPicker() {
        val launchIntent = Intent("com.example.rgbcolorpicker.ACTION_COLOR")
        launchIntent.putExtra("position", colorPosition)
        startActivityForResult(launchIntent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val extras = data?.extras
        Log.i("RETURN", "Return OK")
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            buttonImage1.visibility = View.INVISIBLE
            buildIcon.visibility = View.INVISIBLE
            picker_button.visibility = View.INVISIBLE
            textRed.visibility = View.VISIBLE
            textGreen.visibility = View.VISIBLE
            textBlue.visibility = View.VISIBLE
            textHex.visibility = View.VISIBLE
            editRed.visibility = View.VISIBLE
            editGreen.visibility = View.VISIBLE
            editBlue.visibility = View.VISIBLE
            editHex.visibility = View.VISIBLE
            nameText.visibility = View.VISIBLE
            set_button.visibility = View.VISIBLE
            val colorView = findViewById<ImageView>(R.id.build_color_block)
            redValue = extras!!.getInt("red")
            editRed.setText(redValue.toString())
            greenValue = extras.getInt("green")
            editGreen.setText(greenValue.toString())
            blueValue = extras.getInt("blue")
            editBlue.setText(blueValue.toString())
            val newColor =  Color.rgb(redValue, greenValue, blueValue)
            colorView.setBackgroundColor(newColor)
            val hexValue = String.format("#%02X%02X%02X", redValue, greenValue, blueValue)
            editHex.setText(hexValue)
        }
    }
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
