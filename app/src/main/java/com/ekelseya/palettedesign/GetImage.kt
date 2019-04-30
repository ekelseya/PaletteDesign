package com.ekelseya.palettedesign

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.get_image.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class GetImage: Activity(), View.OnClickListener {
    private val GALLERY = 1
    private val CAMERA = 2

    //TODO: Missing toolbar with back button

    //TODO: layout is gross! Change to reflect app theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_image)

        image_button.setOnClickListener(this)
        ok_button.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.image_button -> {showPictureDialog()}
                //TODO: set ok_button onClick for intent to bitmap activity for color
                R.id.ok_button -> {}
                else -> println("No case satisfied")
            }
        }
    }

    private fun showPictureDialog(){
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    saveImage(bitmap)
                    Toast.makeText(this@GetImage, "Click OK to choose color!", Toast.LENGTH_LONG).show()
                    pictureImageview.setImageBitmap(bitmap)
                    image_button.visibility = View.INVISIBLE
                    ok_button.visibility = View.VISIBLE
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@GetImage, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            pictureImageview.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@GetImage, "Click OK to choose color!", Toast.LENGTH_LONG).show()
            image_button.visibility = View.INVISIBLE
            ok_button.visibility = View.VISIBLE
        }
    }

    private fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + COLOR_IMAGE)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private const val COLOR_IMAGE = "/image"
    }
}