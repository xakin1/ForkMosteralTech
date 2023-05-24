package com.apm.monsteraltech.ui.activities.main.fragments.add

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.*
import com.apm.monsteraltech.services.ProductService
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.camera.CameraActivity
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.apm.monsteraltech.ui.activities.main.MainActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


//TODO: Limitar bien las fotos y los caracteres de la descripción y titulo.
class AddFragment : Fragment() {

    private lateinit var imageGridView: GridView
    private lateinit var imageAdapter: ImageAdapter
    private var selectedImages: ArrayList<Uri> = ArrayList()
    private var PICK_IMAGE_MULTIPLE = 8
    private var MAX_DESCRIPTION_LENGTH = 200
    private var MAX_TITLE_LENGTH = 50
    private var serviceFactory = ServiceFactory()
    private var productService = serviceFactory.createService(ProductService::class.java)
    private var userService = serviceFactory.createService(UserService::class.java)



    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            data?.let { intentData ->
                val images = intentData.getParcelableArrayListExtra<Uri>("selectedImages")
                if (images != null) {
                    selectedImages.addAll(images)
                    imageAdapter.notifyDataSetChanged()
                }
            }
            Log.d("AddFragment", "Imagenes seleccionadas: ${selectedImages.size}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)


        // Inicializa los componentes de la UI
        val addImageButton = view.findViewById<Button>(R.id.add_image_button)
        imageGridView = view.findViewById(R.id.image_gridview)
        val nameEditText = view.findViewById<EditText>(R.id.name_edittext)
        val descriptionEditText = view.findViewById<EditText>(R.id.description_edittext)
        val priceEditText = view.findViewById<EditText>(R.id.price_edittext)
        val categorySpinner = view.findViewById<Spinner>(R.id.category_spinner)
        val estadoSpinner = view.findViewById<Spinner>(R.id.estado_spinner)
        val editTextKm = view.findViewById<EditText>(R.id.km_edittext)
        val editTextM2 = view.findViewById<EditText>(R.id.m2_edittext)
        val addButton = view.findViewById<Button>(R.id.add_button)

        descriptionEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_DESCRIPTION_LENGTH))
        nameEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_TITLE_LENGTH))

        // Inicializa el adaptador de imágenes
        selectedImages = ArrayList()
        imageAdapter = ImageAdapter(requireContext(), selectedImages)
        imageGridView.adapter = imageAdapter


        // Controlamos el boton de agregar imagenes
        addImageButton.setOnClickListener {
            if (selectedImages.size >= PICK_IMAGE_MULTIPLE) {
                Toast.makeText(
                    requireContext(),
                    "No puedes tener más de 8 imágenes",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val intent = Intent(requireContext(), CameraActivity::class.java)
                intent.putExtra("MAX_IMAGES", PICK_IMAGE_MULTIPLE - selectedImages.size)
                startForResult.launch(intent)
            }
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                if (selectedOption == "Coches") {
                    editTextKm.visibility = View.VISIBLE
                } else {
                    editTextKm.visibility = View.GONE
                }
                if (selectedOption == "Casas") {
                    editTextM2.visibility = View.VISIBLE
                } else {
                    editTextM2.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó ninguna opción
            }
        }

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val price = priceEditText.text.toString()
            val category = categorySpinner.selectedItem.toString()
            var estado = estadoSpinner.selectedItem.toString()
            val km = editTextKm.text.toString()
            val m2 = editTextM2.text.toString()

            if (name.isEmpty() || description.isEmpty() || price.isEmpty() || (category == "Coches" && km.isEmpty()) || (category == "Casas" && m2.isEmpty())) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                when (estado) {
                    "Nuevo" -> {
                        estado = "NEW"
                    }
                    "Semi-nuevo" -> {
                        estado = "SEMI_NEW"
                    }
                    "Segunda mano" -> {
                        estado = "SECOND_HAND"
                    }
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        getUserDataFromDatastore().collect { userData: User ->
                            val userBd =
                                userData.firebaseToken.let { userService.getUserByToken(it) }
                            var product: Product? = null
                            when (category) {
                                "Coches" -> {
                                    var car = Car(
                                        name,
                                        description,
                                        price.toDouble(),
                                        estado,
                                        km.toInt(),
                                        userBd
                                    )
                                    product = productService.addCar(
                                        car
                                    )
                                    Toast.makeText(
                                        requireContext(),
                                        "Producto agregado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                "Casas" -> {
                                    var house = House(
                                        name,
                                        description,
                                        price.toDouble(),
                                        estado,
                                        m2.toInt(),
                                        userBd
                                    )
                                    product = productService.addHouse(
                                        house
                                    )
                                }
                                "Electrodomésticos" -> {
                                    var appliance = Appliance(
                                        name,
                                        description,
                                        price.toDouble(),
                                        estado,
                                        userBd
                                    )
                                    product = productService.addAppliance(
                                        appliance
                                    )
                                }
                                "Muebles" -> {
                                    var furniture = Furniture(
                                        name,
                                        description,
                                        price.toDouble(),
                                        estado,
                                        userBd
                                    )
                                    product = productService.addFurniture(
                                        furniture
                                    )
                                }
                            }

                            if (product != null) {
                                lifecycleScope.launch(Dispatchers.IO) {
                                        for (image in selectedImages) {
                                            try {
                                                val drawable = Glide.with(requireContext())
                                                    .asBitmap()
                                                    .load(image)
                                                    .submit()
                                                    .get()
                                                if (drawable != null) {
                                                    val stream = ByteArrayOutputStream()
                                                    drawable.compress(
                                                        Bitmap.CompressFormat.PNG,
                                                        100,
                                                        stream
                                                    )
                                                    val productImage = ProductImage(
                                                        null,
                                                        "imageProduct-" + product.id.toString(),
                                                        "png",
                                                        drawable.byteCount.toLong(),
                                                        stream.toByteArray(),
                                                        product
                                                    )
                                                    productService.addProductImage(productImage)
                                                }
                                            } catch (e: Exception) {
                                                Log.e("Error", e.message.toString())
                                            }
                                        }
                                        val intent = Intent(requireContext(), MainActivity::class.java)
                                        startActivity(intent)
                                    }
                            }

                        }
                    }
                }
            }

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

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
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
    private fun getUserDataFromDatastore()  = requireContext().dataStore.data.map { preferences  ->
        User(
            id = "",
            name = preferences[stringPreferencesKey("userName")].orEmpty(),
            surname = preferences[stringPreferencesKey("userLastname")].orEmpty(),
            firebaseToken = preferences[stringPreferencesKey("userFirebaseKey")].orEmpty(),
            location = null,
            expirationDatefirebaseToken = null
        )
    }

}