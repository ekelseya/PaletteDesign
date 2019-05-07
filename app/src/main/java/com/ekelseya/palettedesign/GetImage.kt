package com.ekelseya.palettedesign

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider.getUriForFile
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.color_block_build.*
import kotlinx.android.synthetic.main.get_image.*
import java.io.File
import java.util.concurrent.ThreadLocalRandom

class GetImage: AppCompatActivity(), View.OnClickListener {

    private var selectedPhotoPath: Uri? = null
    private var pictureTaken: Boolean = false
    private var bitmapWidth = 100
    private var bitmapHeight = 100
    private var redValue = 0
    private var blueValue = 0
    private var greenValue = 0
    private var selectedImageBitmap: Bitmap? = null
    private var colorPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_image)

        val info = intent.extras
        if (info != null) {
            if (info.containsKey("position")) {
                colorPosition = info.getInt("position")
            }
        }

        imageButton.setOnClickListener(this)
        ok_button.setOnClickListener(this)
        button_setColor.setOnClickListener(this)

    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.buttonImage -> {takePhotoFromCamera() }
                R.id.ok_button -> {getColor()}
                R.id.button_setColor -> {returnColor()}
                else -> println("No case satisfied")
            }
        }
    }

    private fun returnColor(){
        val intent = Intent(this, ColorBuild::class.java)
        Log.i("intent", "colorbuild")
        intent.putExtra("position", colorPosition)
        intent.putExtra("red", redValue)
        intent.putExtra("green", greenValue)
        intent.putExtra("blue", blueValue)
        startActivity(intent)
    }

    //Gallery isn't working; commented out for now
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun showPictureDialog(){
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun takePhotoFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imagePath = File(filesDir, "images")
        val newFile = File(imagePath, "default_image.jpg")
        if (newFile.exists()) {
            newFile.delete()
        } else {
            newFile.parentFile.mkdirs()
        }
        selectedPhotoPath = getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile)

        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, selectedPhotoPath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            val clip = ClipData.newUri(contentResolver, "A photo", selectedPhotoPath)
            cameraIntent.clipData = clip
            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        startActivityForResult(cameraIntent, CAMERA)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        //Gallery intent isn't writing to bitmap; commented out for now
/*        if (requestCode == GALLERY) {
            if (resultCode == Activity.RESULT_OK){
                if (intent != null){
                    selectedPhotoPath = intent.data
                }
            }
            Toast.makeText(this@GetImage, "Click New Color to get color from image!", Toast.LENGTH_LONG).show()
            image_button.visibility = View.INVISIBLE
            ok_button.visibility = View.VISIBLE
            button_setColor.visibility = View.VISIBLE
            setImageViewWithImage()
            //TODO: setImageView isn't working with gallery
        }
        else */if (requestCode == CAMERA)
        {
            setImageViewWithImage()
            Toast.makeText(this@GetImage, "Click New Color to get color from image!", Toast.LENGTH_LONG).show()
            imageCamera.visibility = View.INVISIBLE
            buttonImage.visibility = View.INVISIBLE
            ok_button.visibility = View.VISIBLE
            button_setColor.visibility = View.VISIBLE
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setImageViewWithImage() {
        val photoPath: Uri = selectedPhotoPath ?: return
        pictureImageview.post {
            val pictureBitmap = BitmapResizer.shrinkBitmap(
                this@GetImage,
                photoPath,
                pictureImageview.width,
                pictureImageview.height
            )
            pictureImageview.setImageDrawable(null)
            pictureImageview.setImageBitmap(pictureBitmap)
            selectedImageBitmap = pictureBitmap
        }
        pictureTaken = true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getColor() {
        //TODO: now x and y are randomly generated
        val x = ThreadLocalRandom.current().nextInt(0, bitmapWidth)
        val y = ThreadLocalRandom.current().nextInt(0, bitmapHeight)
        val pixel = selectedImageBitmap!!.getPixel(x, y)
        Log.i("pixel", pixel.toString())
        redValue = Color.red(pixel)
        blueValue = Color.blue(pixel)
        greenValue = Color.green(pixel)

        val previewColor = findViewById<ImageView>(R.id.preview_color)
        previewColor.setBackgroundColor(pixel)
        Log.i("getColor", "called")
    }

    companion object {
        private const val COLOR_IMAGE = "/image"
        private const val GALLERY = 1
        private const val CAMERA = 2
    }
}