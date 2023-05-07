package com.apm.monsteraltech.ui.add

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.apm.monsteraltech.CameraActivity
import com.apm.monsteraltech.R
import com.apm.monsteraltech.RegisterActivity
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

//TODO: Limitar bien las fotos y los caracteres de la descripción y titulo.
class AddFragment : Fragment() {
/*    private lateinit var btnSubmit: Button
    private lateinit var btnClear: Button*/

    private lateinit var imageGridView: GridView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var selectedImages: ArrayList<Uri>
    private var PICK_IMAGE_MULTIPLE = 8
    private var MAX_DESCRIPTION_LENGTH = 200
    private var MAX_TITLE_LENGTH = 50


    private val openGalleryForImages = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if (count > PICK_IMAGE_MULTIPLE) {
                    Toast.makeText(
                        requireContext(),
                        "No puedes seleccionar más de 8 imágenes",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@registerForActivityResult
                } else {
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        selectedImages.add(imageUri)
                    }
                }

            } else if (data?.data != null) {
                val imageUri = data.data!!
                selectedImages.add(imageUri)
            }
            Log.d("AddFragment", "Imagenes seleccionadas: ${selectedImages[0]}")
            imageAdapter.notifyDataSetChanged()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Inicializa los componentes de la UI
        val addImageButton = view.findViewById<Button>(R.id.add_image_button)
        imageGridView = view.findViewById<GridView>(R.id.image_gridview)
        val nameEditText = view.findViewById<EditText>(R.id.name_edittext)
        val descriptionEditText = view.findViewById<EditText>(R.id.description_edittext)
        val priceEditText = view.findViewById<EditText>(R.id.price_edittext)
        val categorySpinner = view.findViewById<Spinner>(R.id.category_spinner)
        val addButton = view.findViewById<Button>(R.id.add_button)

        descriptionEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_DESCRIPTION_LENGTH))
        nameEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_TITLE_LENGTH))

        // Inicializa el adaptador de imágenes
        selectedImages = ArrayList()
        imageAdapter = ImageAdapter(requireContext(), selectedImages)
        imageGridView.adapter = imageAdapter




        // Controlamos el boton de agregar imagenes
        addImageButton.setOnClickListener {
            val options = arrayOf<CharSequence>("Tomar foto", "Seleccionar de la galería", "Cancelar")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Elige una opción")
            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == "Tomar foto" -> {
                        val intent = Intent(requireContext(), CameraActivity::class.java)
                        startActivity(intent)
                    }
                    options[item] == "Seleccionar de la galería" -> {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
                        openGalleryForImages.launch(intent)
                        Log.d("AddFragment", "Imagenes seleccionadas: ${selectedImages.size}")
                    }
                    else -> {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()

        }





        // Inflate the layout for this fragment
        return view



    }

    private class ImageAdapter(private val context: Context, private val images: ArrayList<Uri>) : BaseAdapter() {

        var layoutInflater: LayoutInflater? = null
        var MAX_IMAGES = 8
        override fun getCount(): Int {
            if (images.size > MAX_IMAGES) {
                Toast.makeText(
                    context,
                    "No puedes tener mas de 8 imagenes",
                    Toast.LENGTH_SHORT
                ).show()
                return MAX_IMAGES
            }
            return images.size
        }

        override fun getItem(position: Int): Any {
            return images[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.grid_item_image, parent, false)
            }

            val imageView = view!!.findViewById<ImageView>(R.id.ivImagen)
            val imageButton = view.findViewById<ImageButton>(R.id.ibtnEliminar)
            imageView.layoutParams = ViewGroup.LayoutParams(500, 500)
            imageView.adjustViewBounds = true

            Glide.with(context)
                .load(images[position])
                .centerCrop()
                .into(imageView)

            imageButton.setOnClickListener {
                images.removeAt(position)
                notifyDataSetChanged()
            }
            return view
        }

    }

}