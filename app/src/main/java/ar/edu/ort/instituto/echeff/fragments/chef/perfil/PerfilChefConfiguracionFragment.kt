package ar.edu.ort.instituto.echeff.fragments.chef.perfil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.Comentario
import ar.edu.ort.instituto.echeff.entities.Historia
import ar.edu.ort.instituto.echeff.entities.PerfilChef
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelDetalleReservaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelFormularioPropuestaFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelPerfilChefConfiguracionFragment
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReservasConfirmarFragment
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PerfilChefConfiguracionFragment : Fragment(), StorageReferenceUtiles {

    private lateinit var viewModel: ViewModelPerfilChefConfiguracionFragment
    lateinit var v: View
    lateinit var btnAgregarFotoPerfil: Button
    lateinit var btnVolverAlPerfil: Button
    lateinit var txaBiografia: TextView
    lateinit var lblContadorBio: TextView
    lateinit var btnGuardarBiografia: Button
    lateinit var btnAgregarFotoHistoria: Button
    lateinit var txaAgregarComentarioHistoria: TextView
    lateinit var lblContadorComentarioHistoria: TextView
    lateinit var btnAgregarHistoria: Button
    lateinit var btn_EditarPerfil: FloatingActionButton
    var urlFoto : String = ""
    var urlFotoHistoria : String = ""
    private  var historiasPhotoURI: Uri? = null
    private lateinit var currentPhotoPath: String
    private  var chefPhotoURI: Uri? = null

    var chef = Chef()
    lateinit var perfil: PerfilChef

    var historia: Historia = Historia()

    var buscar = false
    var nuevo = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo conectar con firebase
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_perfil_chef_configuracion, container, false)

        btnAgregarFotoPerfil = v.findViewById(R.id.btnAgregarFotoPerfil)
        btnVolverAlPerfil = v.findViewById(R.id.btnVolverAlPerfil)
        txaBiografia = v.findViewById(R.id.txaBiografia)
        lblContadorBio = v.findViewById(R.id.lblContadorBio)
        btnGuardarBiografia = v.findViewById(R.id.btnGuardarBiografia)
        btnAgregarFotoHistoria = v.findViewById(R.id.btnAgregarFotoHistoria)
        txaAgregarComentarioHistoria = v.findViewById(R.id.txaAgregarComentarioHistoria)
        lblContadorComentarioHistoria = v.findViewById(R.id.lblContadorComentarioHistoria)
        btnAgregarHistoria = v.findViewById(R.id.btnAgregarHistoria)
        btn_EditarPerfil = v.findViewById(R.id.fab_editar)


        btnGuardarBiografia.visibility = View.INVISIBLE;
        txaBiografia.isFocusable = false
        txaBiografia.isEnabled = false
        txaAgregarComentarioHistoria.isFocusable = false
        txaAgregarComentarioHistoria.isEnabled = false
        lblContadorComentarioHistoria.isFocusable = false
        btnAgregarFotoHistoria.visibility = View.INVISIBLE
        btnAgregarHistoria.visibility = View.INVISIBLE
        txaAgregarComentarioHistoria.visibility = View.INVISIBLE
        lblContadorComentarioHistoria.visibility = View.INVISIBLE
        btnVolverAlPerfil.visibility = View.INVISIBLE

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(ViewModelPerfilChefConfiguracionFragment::class.java)

        viewModel.dataPerfil.observe(viewLifecycleOwner, Observer { result ->

            perfil = result
            if (perfil.id.equals("")) {
                nuevo = true
            }

            llenarFichaPerfil()
        })
    }

    override fun onStart() {
        super.onStart()

        var sharedPref: SharedPreferences =
            requireContext().getSharedPreferences(EcheffUtilities.PREF_NAME.valor,
                Context.MODE_PRIVATE)
        var idUsuario: String = sharedPref.getString("userId", "Vacio")!!

        viewModel.setBuscar(idUsuario)

        btn_EditarPerfil.setOnClickListener() {
            //HABILITO LOS BOTONES Y CUADROS DE TEXTO
            txaBiografia.isFocusable = true
            txaBiografia.isEnabled = true
            txaBiografia.isClickable = true
            txaBiografia.isCursorVisible = true
            txaBiografia.isFocusableInTouchMode = true

            btnGuardarBiografia.visibility = View.VISIBLE;
            txaAgregarComentarioHistoria.isFocusable = true
            txaAgregarComentarioHistoria.isEnabled = true
            txaAgregarComentarioHistoria.isClickable = true
            txaAgregarComentarioHistoria.isCursorVisible = true
            txaAgregarComentarioHistoria.isFocusableInTouchMode = true

            btnAgregarFotoHistoria.visibility = View.VISIBLE
            btnAgregarHistoria.visibility = View.VISIBLE
            txaAgregarComentarioHistoria.visibility = View.VISIBLE
            lblContadorComentarioHistoria.visibility = View.VISIBLE

        }

        btnAgregarFotoHistoria.setOnClickListener() {
            var carpeta = "historias_Pics"
            dispatchTakePictureIntent(carpeta, 4444)
        }

        btnAgregarFotoPerfil.setOnClickListener() {

            var carpeta = "profilePics"
            dispatchTakePictureIntent(carpeta, 5555)
            btnVolverAlPerfil.visibility = View.VISIBLE
            btnAgregarFotoHistoria.visibility = View.INVISIBLE

        }

        btnVolverAlPerfil.setOnClickListener() {
            chef = viewModel.chef.value!!

            var carpeta = "profilePics"
            var carpeta2 = "historias_Pics"

            if (chefPhotoURI != null ) {
                urlFoto = uploadImage(chefPhotoURI!!, carpeta)
                chef.urlFoto = urlFoto
                viewModel.actualizarChef(chef)
            }


            if (historiasPhotoURI != null) {
                urlFotoHistoria = uploadImage(historiasPhotoURI!!, carpeta2)
                armarHistoria(idUsuario)
                viewModel.agregarHistoria(historia)

            }

            val perfilChef =
                PerfilChefConfiguracionFragmentDirections.actionPerfilChefConfiguracionFragmentToPerfilChefFragment(chef);
            v.findNavController().navigate(perfilChef)

        }

        btnGuardarBiografia.setOnClickListener() {
            perfil.bio = txaBiografia.text.toString()
            if (nuevo) {
                perfil.meGusta = 0
                perfil.idChef = idUsuario
                viewModel.agregarPerfil(perfil)
            } else {
                viewModel.actualizarPerfil(perfil)
            }
            btnGuardarBiografia.visibility = View.INVISIBLE
            txaBiografia.isFocusable = false
            txaBiografia.isEnabled = false

        }

        btnAgregarHistoria.setOnClickListener() {

            txaAgregarComentarioHistoria.isFocusable = false
            txaAgregarComentarioHistoria.isEnabled = false
            btnAgregarFotoHistoria.visibility = View.INVISIBLE
            btnAgregarHistoria.visibility = View.INVISIBLE
            txaAgregarComentarioHistoria.visibility = View.INVISIBLE
            lblContadorComentarioHistoria.visibility = View.INVISIBLE
            btnVolverAlPerfil.visibility = View.VISIBLE
            btnAgregarFotoHistoria.visibility = View.INVISIBLE
        }
    }

    fun llenarFichaPerfil() {
        txaBiografia.text = perfil.bio
        lblContadorBio.text = perfil.bio.length.toString()
    }

    fun armarHistoria(idUsuario: String) {
        this.historia.idChef = idUsuario
        this.historia.cantidadMegusta = 0
        this.historia.comentario = txaAgregarComentarioHistoria.text.toString()
        this.historia.urlImagen = urlFotoHistoria

    }

    private fun dispatchTakePictureIntent(folder: String, requestCode: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(folder)
                } catch (ex: IOException) {
                    throw ex
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    if (requestCode == 4444) {
                        historiasPhotoURI = FileProvider.getUriForFile(
                            v.context,
                            "ar.edu.ort.instituto.echeff.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, historiasPhotoURI)

                        startActivityForResult(takePictureIntent, requestCode)
                    }
                    if (requestCode == 5555) {
                        chefPhotoURI = FileProvider.getUriForFile(
                            v.context,
                            "ar.edu.ort.instituto.echeff.fileprovider",
                            it
                        )

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, chefPhotoURI)
                        startActivityForResult(takePictureIntent, requestCode)
                    }

                }

            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(folder: String): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES + folder)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


}