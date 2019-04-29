package com.ekelseya.palettedesign

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider.getUriForFile
import android.view.View
import kotlinx.android.synthetic.main.get_image.*
import java.io.File

class GetImage: Activity(), View.OnClickListener {
    private var selectedPhotoPath: Uri? = null
    private var pictureTaken: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_image)
        //TODO: Add drawable to pictureImageview

        pictureImageview.setOnClickListener(this)
        get_image_button.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.pictureImageview -> {takePictureWithCamera()}
                R.id.get_image_button -> {}
                else -> println("No case satisfied")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun takePictureWithCamera(){
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val imagePath = File(filesDir, "images")
        val newFile = File(imagePath, "default_image.jpg")
        if (newFile.exists()){
            newFile.delete()
        } else {
            newFile.parentFile.mkdirs()
        }
        selectedPhotoPath = getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile)

        captureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, selectedPhotoPath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            val clip = ClipData.newUri(contentResolver, "A photo", selectedPhotoPath)
            captureIntent.clipData = clip
            captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        startActivityForResult(captureIntent, TAKE_PHOTO_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            setImageViewWithImage()
        }
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
            pictureImageview.setImageBitmap(pictureBitmap)
        }
        text_instructions_2.visibility = View.VISIBLE
        pictureTaken = true
    }

    companion object {
        private const val COLOR_IMAGE = "image/"
        private const val TAKE_PHOTO_REQUEST_CODE = 1
    }
}