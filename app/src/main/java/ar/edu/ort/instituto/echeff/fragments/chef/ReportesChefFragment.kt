package ar.edu.ort.instituto.echeff.fragments.chef

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Chef
import ar.edu.ort.instituto.echeff.entities.EstadoPropuesta
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.fragments.chef.viewmodel.ViewModelReportesChefFragment
import ar.edu.ort.instituto.echeff.utils.StorageReferenceUtiles
import ar.edu.ort.instituto.echeff.utils.EcheffUtilities
import ar.edu.ort.instituto.echeff.utils.GlideApp

class ReportesChefFragment : Fragment(), StorageReferenceUtiles {

    private lateinit var viewModel: ViewModelReportesChefFragment
    lateinit var sharedPreferences: SharedPreferences

    lateinit var v: View
    lateinit var imgChefPerfil: ImageView
    lateinit var lblNombreChef: TextView
    lateinit var lblPropuestasConfirmadas: TextView
    lateinit var lblPropuestasRechazadas: TextView
    lateinit var lblPropuestasRealizadas: TextView
    lateinit var lblPorcentajeAceptacion: TextView
    lateinit var lblPropuestasCreadas: TextView
    lateinit var lblImporteObtenida: TextView
    lateinit var lblImportePendiente: TextView
    lateinit var lblImporteXComensal: TextView

    private var propuestaList : MutableList<Propuesta> = ArrayList<Propuesta>()

    var chef = Chef()
    var confirmadas = 0.0
    var rechazadas = 0.0
    var finalizadas = 0.0
    var efectividad = 0.0
    var importeObtenido = 0.0
    var importeARecibir = 0.0
    var importePromedio = 0.0


    var cargado : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelReportesChefFragment::class.java)

        viewModel.cargar.observe(viewLifecycleOwner, Observer { result ->

            cargado = result

        })

        viewModel.liveDataList.observe(viewLifecycleOwner, Observer { result ->

            propuestaList = result
            chef = viewModel.chef.value!!

            confirmadas = 0.0
            rechazadas = 0.0
            finalizadas = 0.0
            efectividad = 0.0
            importeObtenido = 0.0
            var comensales = 0.0

            for (propuesta in propuestaList) {
               if (propuesta.idEstadoPropuesta == EstadoPropuesta.PAGADO.id) {
                   confirmadas+=1
                   importeARecibir += propuesta.importeTotal!!
                   comensales += propuesta.importeTotal!!/ propuesta.total

               }
               if (propuesta.idEstadoPropuesta == EstadoPropuesta.RECHAZADO.id) {
                   rechazadas+=1
               }
               if (propuesta.idEstadoPropuesta == EstadoPropuesta.FINALIZADO.id) {
                   finalizadas+=1
                   importeObtenido += propuesta.importeTotal!!
                   comensales += propuesta.importeTotal!!/ propuesta.total

               }
           }
            efectividad = 0.0
            if (propuestaList.size!=0) {
                efectividad = ((confirmadas + finalizadas) / propuestaList.size) * 100
            }

            importePromedio = 0.0
            importePromedio = (importeObtenido + importeARecibir) / (comensales)

            llenarDatos()

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_reportes_chef, container, false)

        imgChefPerfil = v.findViewById(R.id.imgChefPerfil)
        lblNombreChef = v.findViewById(R.id.lblNombreChef)
        lblPropuestasConfirmadas = v.findViewById(R.id.tv_datosPropuestasRealizadas)
        lblPropuestasRechazadas = v.findViewById(R.id.tv_datosPropuestasRechazadas)
        lblPropuestasRealizadas = v.findViewById(R.id.tv_datosPropuestasRealizadas)
        lblPorcentajeAceptacion = v.findViewById(R.id.tv_DatoEfectividadPropuestas)
        lblPropuestasCreadas = v.findViewById(R.id.tv_datosPropuestasCreadas)
        lblImporteObtenida = v.findViewById(R.id.tv_DatoImporteObtenido)
        lblImportePendiente = v.findViewById(R.id.tv_DatoImportePendiente)
        lblImporteXComensal = v.findViewById(R.id.tv_DatoImportePromedio)


        return v
    }

    override fun onStart() {
        super.onStart()

        this.setSharedPreferences()

        val idUsuario : String  = sharedPreferences.getString("userId","Vacio")!!
        var nombreUsuario : String = sharedPreferences.getString("userDisplayName","Nombre No encontrado")!!

        viewModel.setcargar(idUsuario)
        lblNombreChef.text = nombreUsuario

    }

    fun llenarDatos() {

        lblPropuestasCreadas.text = propuestaList.size.toString()
        lblPropuestasConfirmadas.text = confirmadas.toInt().toString()
        lblPropuestasRechazadas.text = rechazadas.toInt().toString()
        lblPropuestasRealizadas.text = finalizadas.toInt().toString()
        lblPorcentajeAceptacion.text = efectividad.toInt().toString() + "%"
        lblImporteObtenida.text = "$ " + importeObtenido.toInt().toString()
        lblImportePendiente.text = "$ " + importeARecibir.toInt().toString()
        lblImporteXComensal.text = "$ " + importePromedio.toInt().toString()


        val url : String
        url = if (chef.urlFoto.isNotEmpty()) {
            chef.urlFoto
        } else {
            EcheffUtilities.SIN_FOTO.valor
        }

        GlideApp.with(this)
            .load(buscarReferencia(url))
            .centerInside()
            .into(imgChefPerfil);

    }

    private fun setSharedPreferences() {
        this.sharedPreferences = this.activity!!.getSharedPreferences(
            EcheffUtilities.PREF_NAME.valor,
            AppCompatActivity.MODE_PRIVATE
        )
    }

}