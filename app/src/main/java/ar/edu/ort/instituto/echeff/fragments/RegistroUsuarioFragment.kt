package ar.edu.ort.instituto.echeff.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Cliente
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class RegistroUsuarioFragment : Fragment() {

    val db = Firebase.firestore
    var storage = FirebaseStorage.getInstance()


    lateinit var v: View
    lateinit var checkBoxSoyChef: CheckBox
    lateinit var nombre: EditText
    lateinit var telefono: EditText
    lateinit var buttonRegistro: Button
    lateinit var buttonProfilePic: Button
    lateinit var imageViewProfilePic: ImageView
    lateinit var imageViewChefDiploma: ImageView
    lateinit var buttonChefDiploma: Button
    lateinit var textViewDiploma: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var profilePhotoURI: Uri
    private var diplomaPhotoURI: Uri? = null



    fun goToInicio() {
        val isChef = sharedPreferences.getBoolean("isChef", false)
        val action = if (isChef) {
            RegistroUsuarioFragmentDirections.actionRegistroUsuarioFragmentToHomeClienteFragment()
        } else {
            RegistroUsuarioFragmentDirections.actionRegistroUsuarioFragmentToHomeChefFragment2()
        }
        v.findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_registro_usuario, container, false)
        checkBoxSoyChef = v.findViewById(R.id.checkBoxRegistroClienteSoyChef)
        nombre = v.findViewById(R.id.editTextNombreRegistroCliente)
        buttonProfilePic = v.findViewById(R.id.buttonProfilePic)
        imageViewProfilePic = v.findViewById(R.id.imageViewProfilePic)
        telefono = v.findViewById(R.id.editTextTelefonoRegistroCliente)
        buttonRegistro = v.findViewById(R.id.buttonRegistroCliente)
        buttonChefDiploma = v.findViewById(R.id.buttonChefDiploma)
        imageViewChefDiploma = v.findViewById(R.id.imageViewChefDiploma)
        textViewDiploma = v.findViewById(R.id.textViewDiploma)

        return v
    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            "MySharedPref",
            AppCompatActivity.MODE_PRIVATE
        )
        this.editor = sharedPreferences.edit()
    }

    override fun onStart() {
        super.onStart()
        setSharedPreferences()
        if (!sharedPreferences.getBoolean("isNew", true)) goToInicio()
        val userId = sharedPreferences.getString("userId", null).orEmpty()
        val userDisplayName = sharedPreferences.getString("userDisplayName", null).orEmpty()
        val userEmail  = sharedPreferences.getString("userEmail", null).orEmpty()

        nombre.setText(userDisplayName, TextView.BufferType.EDITABLE)

        checkBoxSoyChef.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                imageViewChefDiploma.visibility = View.VISIBLE
                buttonChefDiploma.visibility = View.VISIBLE
                textViewDiploma.visibility = View.VISIBLE
            } else {
                imageViewChefDiploma.visibility = View.INVISIBLE
                buttonChefDiploma.visibility = View.INVISIBLE
                textViewDiploma.visibility = View.INVISIBLE
            }
        }

        buttonRegistro.setOnClickListener {
            val profilePhotoRef = uploadImage(profilePhotoURI, "profilePics")

            val isChef = checkBoxSoyChef.isActivated
            if(isChef) {
                val diplomaPhotoRef = uploadImage(diplomaPhotoURI!!, "diplomas")
                db.collection("chefs").add(
                    Cliente(
                        userId,
                        nombre.text.toString(),
                        userEmail,
                        profilePhotoRef,
                        "",
                        telefono.text.toString()
                    )
                )
            } else {
                db.collection("usuarios").add(
                    Cliente(
                        userId,
                        userDisplayName,
                        userEmail,
                        profilePhotoRef,
                        "",
                        telefono.text.toString()
                    )
                )
            }

            editor.putBoolean("isChef", isChef)
            editor.commit()
            goToInicio()
        }

        buttonChefDiploma.setOnClickListener {
            dispatchTakePictureIntent("diplomas", 12345)
        }
        buttonProfilePic.setOnClickListener {
            dispatchTakePictureIntent("profilePics", 1234)
        }
    }
    private fun dispatchTakePictureIntent(folder: String, requestCode: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(this.activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(folder)
                } catch (ex: IOException) {
                    throw ex
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    if (requestCode == 1234) {
                        profilePhotoURI = FileProvider.getUriForFile(
                            v.context,
                            "ar.edu.ort.instituto.echeff.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, profilePhotoURI)
                        startActivityForResult(takePictureIntent, requestCode)
                    } else {
                        diplomaPhotoURI = FileProvider.getUriForFile(
                            v.context,
                            "ar.edu.ort.instituto.echeff.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, diplomaPhotoURI)
                        startActivityForResult(takePictureIntent, requestCode)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            imageViewProfilePic.setImageURI(profilePhotoURI)
        } else if (requestCode == 12345 && resultCode == RESULT_OK) {
            imageViewChefDiploma.setImageURI(diplomaPhotoURI)
        }
    }
    private lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(folder: String): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this.activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES + folder)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun uploadImage(uri: Uri, folder: String): String {
        val storageRef = storage.reference
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val referenceName = "$folder/$timeStamp.jpg"
        val imageRef = storageRef.child(referenceName)
        imageRef.putFile(uri)
        return referenceName
    }


}