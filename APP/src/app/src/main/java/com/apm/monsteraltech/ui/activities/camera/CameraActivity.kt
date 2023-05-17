package com.apm.monsteraltech.ui.activities.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var selectedImages: ArrayList<Uri>
    private lateinit var imageRecyclerView: RecyclerView
    private var PICK_IMAGE_MULTIPLE = 8
    private var MAX_DESCRIPTION_LENGTH = 200
    private var MAX_TITLE_LENGTH = 50
    private val returnIntent = Intent()


    private val openGalleryForImages = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if (count > PICK_IMAGE_MULTIPLE) {
                    Toast.makeText(
                        baseContext,
                        "No puedes seleccionar más de 8 imágenes",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@registerForActivityResult
                } else {
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        selectedImages.add(imageUri)
                        imageAdapter.notifyItemInserted(selectedImages.size - 1)
                    }
                }

            } else if (data?.data != null) {
                val imageUri = data.data!!
                selectedImages.add(imageUri)
                imageAdapter.notifyItemInserted(selectedImages.size - 1)
            }
            Log.d("CameraActivity", "Imagenes seleccionadas: ${selectedImages[0]}")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allCameraPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permiso non concedido polo usuario.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imageRecyclerView = findViewById(R.id.image_recycler_view)

        PICK_IMAGE_MULTIPLE = intent.getIntExtra("MAX_IMAGES", 8)

        // Inicializa el adaptador de imágenes
        selectedImages = ArrayList()
        imageRecyclerView.adapter = null
        imageAdapter = ImageAdapter(this, selectedImages)
        val llm = GridLayoutManager(this,8,RecyclerView.VERTICAL,false)
        imageRecyclerView.layoutManager = llm
        imageRecyclerView.adapter = imageAdapter



        // Solicitude dos permisos da cámara
        if (allCameraPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        // Listener para a realización de fotos a través do botón (non usado neste exemplo)
        findViewById<Button>(R.id.camera_capture_button).setOnClickListener {
            if (selectedImages.size >= PICK_IMAGE_MULTIPLE) {
                Toast.makeText(
                    baseContext,
                    "No puedes tener más de 8 imágenes",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                takePhoto()
            }
        }

        findViewById<Button>(R.id.storage_image_button).setOnClickListener {
            if (selectedImages.size >= PICK_IMAGE_MULTIPLE) {
                Toast.makeText(
                    baseContext,
                    "No puedes tener más de 8 imágenes",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
                openGalleryForImages.launch(intent)
                Log.d("AddFragment", "Imagenes seleccionadas: ${selectedImages.size}")
            }
        }

        findViewById<Button>(R.id.ok_button).setOnClickListener {
            //Devolver los valores de selectedimages a addfragment
            Log.d("CameraActivity", "Imagenes seleccionadas: ${selectedImages.size}")

            returnIntent.putExtra("selectedImages", selectedImages)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

    }


    private fun takePhoto() {

        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Erro na captura: ${exc.message}", exc)
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    selectedImages.add(savedUri)
                    imageAdapter.notifyDataSetChanged()
                    Log.d("CameraActivity", "Imagenes seleccionadas: ${selectedImages.size}")
                    val msg = "Tomada foto: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(findViewById<PreviewView>(R.id.viewFinder)?.surfaceProvider)
                }
// Engádese o caso de uso da captura
            imageCapture = ImageCapture.Builder()
                .build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
// Engásese o caso de uso da captura
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allCameraPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else this.filesDir
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private class ImageAdapter(private val context: Context, private val images: ArrayList<Uri>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

        private val MAX_IMAGES = 8

        override fun getItemCount(): Int {
            return if (images.size > MAX_IMAGES) {
                Toast.makeText(
                    context,
                    "No puedes tener más de 8 imágenes",
                    Toast.LENGTH_SHORT
                ).show()
                MAX_IMAGES
            } else {
                images.size
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.grid_item_image, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(images[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val imageView: ImageView = itemView.findViewById(R.id.ivImagen)
            private val imageButton: ImageButton = itemView.findViewById(R.id.ibtnEliminar)

            init {
                imageView.layoutParams = ViewGroup.LayoutParams(500, 500)
                //imageView.adjustViewBounds = true
            }

            fun bind(uri: Uri) {
                Glide.with(context)
                    .load(uri)
                    .centerCrop()
                    .into(imageView)

                imageButton.setOnClickListener {
                    images.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

    }
}