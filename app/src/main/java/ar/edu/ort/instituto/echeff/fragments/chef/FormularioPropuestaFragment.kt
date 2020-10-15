package ar.edu.ort.instituto.echeff.fragments.chef

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.dao.propuestasDao
import ar.edu.ort.instituto.echeff.entities.Propuesta
import ar.edu.ort.instituto.echeff.entities.Reserva
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FormularioPropuestaFragment : Fragment(), propuestasDao   {
    lateinit var v: View

    // datos de la reserva
    lateinit var usuario : TextView
    lateinit var comensales :TextView
    lateinit var estilococina :TextView
    lateinit var reserva : Reserva
    lateinit var servicio : TextView

    //los input de la propuesta
    lateinit var editText_Snack : EditText
    lateinit var editText_Entrada : EditText
    lateinit var editText_PlatoPricipal : EditText
    lateinit var editText_Postre : EditText
    lateinit var editText_Adicional : EditText
    lateinit var editText_Importe : EditText

    //botoens de Guardar y Enviar
    lateinit var btn_Propuesta : Button
    lateinit var btn_EditarPropuesta : Button
    lateinit var btn_EnviarPropuesta : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_formulario_propuesta, container, false)
        //datos de la Tarjeta de reserva
        usuario = v.findViewById(R.id.text_DatoUsuario)
        comensales = v.findViewById(R.id.text_DatosComensales)
        estilococina = v.findViewById(R.id.text_DatosEstiloComida)
        servicio = v.findViewById(R.id.text_DatoTipoServicio)


        //Formulario Propuesta
        editText_Snack = v.findViewById(R.id.editText_Snack)
        editText_Entrada = v.findViewById(R.id.editText_Entrada)
        editText_PlatoPricipal = v.findViewById(R.id.editText_PlatoPrincipal)
        editText_Postre = v.findViewById(R.id.editText_Postre)
        editText_Adicional = v.findViewById(R.id.editText_Adicionales)
        editText_Importe = v.findViewById(R.id.editText_Total)

        //Boton de Propuesta
        btn_Propuesta = v.findViewById(R.id.btn_GuardarPropuesta)
        btn_EditarPropuesta = v.findViewById(R.id.btn_EditarPropuesta)
        btn_EnviarPropuesta = v.findViewById(R.id.btn_EnviarPropuesta)

        btn_EnviarPropuesta.setVisibility(View.INVISIBLE);
        btn_EditarPropuesta.setVisibility(View.INVISIBLE);


        return v
    }
    override fun onStart() {
        super.onStart()
        var modificado : Boolean = false

        //Obtengo la reserva que llega por Argumentos de navegacion
        reserva = FormularioPropuestaFragmentArgs.fromBundle(requireArguments()).formularioPropuestaArg

        //lleno los datos de la reserva
        usuario.text = reserva.idUsuario.toString() //Hay que buscar el Usuario
        comensales.text = reserva.comensales.toString()
        estilococina.text = reserva.estiloCocina
        servicio.text = reserva.tipoServicio

        //Boton de Guardar la Propuesta y cambiar los botones y blockear los EditText
        btn_Propuesta.setOnClickListener {
            //guardo la propuesta
           guardarPropuesta(editText_Snack.text.toString(),editText_Entrada.text.toString(),editText_PlatoPricipal.text.toString(),
                            editText_Postre.text.toString(), editText_Adicional.text.toString(),editText_Importe.text.toString().toDouble(),reserva.id, modificado)

            //Cambio los botones y blockeo los textos
            btn_EditarPropuesta.setVisibility(View.VISIBLE);
            btn_EnviarPropuesta.setVisibility(View.VISIBLE);
            btn_Propuesta.setVisibility(View.INVISIBLE);
            editText_Snack.setFocusable(false)
            editText_Entrada.setFocusable(false)
            editText_PlatoPricipal.setFocusable(false)
            editText_Postre.setFocusable(false)
            editText_Adicional.setFocusable(false)
            editText_Importe.setFocusable(false)

        }

        btn_EnviarPropuesta.setOnClickListener {
            val action =
                FormularioPropuestaFragmentDirections.actionFormularioPropuestaFragmentToHomeChefFragment()
            v.findNavController().navigate(action)
        }

        btn_EditarPropuesta.setOnClickListener {
            modificado = true
            btn_EnviarPropuesta.setVisibility(View.INVISIBLE);
            btn_EditarPropuesta.setVisibility(View.INVISIBLE);
            btn_Propuesta.setVisibility(View.VISIBLE);

            editText_Snack.setFocusable(true)
            editText_Snack.setClickable(true)

            editText_Entrada.setFocusable(true)
            editText_PlatoPricipal.setFocusable(true)
            editText_Postre.setFocusable(true)
            editText_Adicional.setFocusable(true)
            editText_Importe.setFocusable(true)


        }

    }

     fun guardarPropuesta(snack:String, entrada:String,platoPrincial:String,postre:String,adicional:String,importe:Double,reservaId: String, modificado : Boolean){
       var propuesta = Propuesta(1,snack,entrada,platoPrincial,postre,adicional,importe,1,reservaId)
         val scope = CoroutineScope(Dispatchers.Default)

         scope.launch {
             if (modificado)
                update(propuesta)
             else
                 add(propuesta)
         }

    }
}