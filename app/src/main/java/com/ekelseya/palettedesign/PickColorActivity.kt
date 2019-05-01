package com.ekelseya.palettedesign

import android.R.attr.x
import android.R.attr.y
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.color_bitmap.*


private var viewBitmap: Bitmap? = null
private var pictureUri: Uri? = null
private var originalImage = false

class PickColorActivity: AppCompatActivity(), View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_bitmap)

            button_setColor.setOnClickListener(this)

            originalImage = true

            pictureUri = intent.getParcelableExtra(IMAGE_URI_KEY)
            val bitmapWidth = intent.getIntExtra(BITMAP_WIDTH, 100)
            val bitmapHeight = intent.getIntExtra(BITMAP_HEIGHT, 100)

            pictureUri?.let {
                val selectedImageBitmap = BitmapResizer.shrinkBitmap(this, it, bitmapWidth, bitmapHeight)
                image_bitmap.setImageBitmap(selectedImageBitmap)

                viewBitmap = selectedImageBitmap
            }

        }

        //TODO: Add function to pull color from bitmap

        @RequiresApi(Build.VERSION_CODES.N)
        override fun onClick(v: View) {
            when (v.id) {
                R.id.button_setColor -> {}
                else -> println("No case satisfied")
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun getColor() {
            //Not working yet
            val pixel = viewBitmap!!.getPixel(x, y)
            val redValue = Color.red(pixel)
            val blueValue = Color.blue(pixel)
            val greenValue = Color.green(pixel)

            val newColor = Color.rgb(redValue, greenValue, blueValue)

            image_color.setBackgroundColor(newColor)

        }
    }

