package com.example.canvas

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.btnClear
import kotlinx.android.synthetic.main.activity_main.btnSave
import kotlinx.android.synthetic.main.activity_main.simpleDrawingView
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

  private val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initView()
  }

  fun initView() {

    btnClear.setOnClickListener { view_ -> simpleDrawingView.clearCanvas() }
    btnSave.setOnClickListener { view_ -> checkPermission() }
  }

  fun checkPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
          this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
          PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
      )
    } else {
      saveImage((System.currentTimeMillis()/1000).toString())
    }
  }

  private fun saveImage(
    fileName: String
  ) {
    val imageDir = "${Environment.DIRECTORY_PICTURES}/canvas/"
    val path = Environment.getExternalStoragePublicDirectory(imageDir)
    Log.e("path", path.toString())
    val file = File(path, "$fileName.jpg")
    path.mkdirs()
    file.createNewFile()
    val outputStream = FileOutputStream(file)

    simpleDrawingView.isDrawingCacheEnabled = true
    val bitmap = simpleDrawingView.drawingCache
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.flush()
    outputStream.close()
  }
}
