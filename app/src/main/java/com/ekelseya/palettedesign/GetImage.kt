package com.ekelseya.palettedesign

import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider.getUriForFile
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.get_image.*
import java.io.File

class GetImage: AppCompatActivity(), View.OnClickListener {

    private var selectedPhotoPath: Uri? = null
    private var pictureTaken: Boolean = false

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
                R.id.ok_button -> {okImage()}
                else -> println("No case satisfied")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
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

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            Toast.makeText(this@GetImage, "Click OK to choose color!", Toast.LENGTH_LONG).show()
            image_button.visibility = View.INVISIBLE
            ok_button.visibility = View.VISIBLE
            setImageViewWithImage()
            //TODO: setImageView isn't working with gallery
        }
        else if (requestCode == CAMERA)
        {
            setImageViewWithImage()
            Toast.makeText(this@GetImage, "Click OK to choose color!", Toast.LENGTH_LONG).show()
            image_button.visibility = View.INVISIBLE
            ok_button.visibility = View.VISIBLE
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
        pictureTaken = true
    }

    private fun okImage() {
        if (pictureTaken) {
            val nextScreenIntent = Intent(this, PickColorActivity::class.java).apply {
                putExtra(IMAGE_URI_KEY, selectedPhotoPath)
                putExtra(BITMAP_WIDTH, pictureImageview.width)
                putExtra(BITMAP_HEIGHT, pictureImageview.height)
            }

            startActivity(nextScreenIntent)
        } else {
            Toast.makeText(this@GetImage, "No image selected!", Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        private const val COLOR_IMAGE = "/image"
        private const val GALLERY = 1
        private const val CAMERA = 2
    }
}