package ar.edu.ort.instituto.echeff.fragments.cliente

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva
import ar.edu.ort.instituto.echeff.validator.Validator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class FormularioReservaFragment : Fragment(), Validator {

    val db = Firebase.firestore

    lateinit var v : View

    lateinit var etFecha : EditText
    lateinit var etHora : EditText
    lateinit var etCantComensales : EditText
    lateinit var etDireccion : EditText
    lateinit var spinnerTipoCocina : Spinner
    lateinit var cbTengoHorno : CheckBox
    lateinit var btnContinuar : Button
    lateinit var imgBtnCalendar : ImageView
    lateinit var imgBtnHour : ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_formulario_reserva, container, false)

        etFecha = v.findViewById(R.id.editText_formularioReserva_fecha)
        etHora = v.findViewById(R.id.editText_formularioReserva_hora)
        etCantComensales = v.findViewById(R.id.editText_formularioReserva_cantidadComensales)
        etDireccion = v.findViewById(R.id.editText_formularioReserva_direccion)
        cbTengoHorno = v.findViewById(R.id.checkBox_formularioReserva_tengoHorno)
        btnContinuar = v.findViewById(R.id.btn_formularioReserva_continuar)
        imgBtnCalendar = v.findViewById(R.id.imageButton_calendarPicker)
        imgBtnHour = v.findViewById(R.id.imageButton_hourPicker)

        spinnerTipoCocina = v.findViewById(R.id.spinner_formularioReserva_tipoCocina)
        val adapter = ArrayAdapter.createFromResource(v.context,R.array.spinner_values_tiposCocina,R.layout.spinner_item)
        spinnerTipoCocina.adapter = adapter

        return v
    }

    override fun onStart() {
        super.onStart()

        
        btnContinuar.setOnClickListener {

            if(validar()){

                var stringFecha = etFecha.text.toString()
                var stringHora = etHora.text.toString()
                var cantComensales = etCantComensales.text.toString().toInt()
                var stringTipoCocina = spinnerTipoCocina.selectedItem.toString()
                var stringDireccion = etDireccion.text.toString()
                var boolTieneHorno = cbTengoHorno.isChecked.toString()

                var reserva = Reserva("", stringFecha,stringHora,stringDireccion,stringTipoCocina,boolTieneHorno.toString(), cantComensales, "","","","",1,"")

                val action2 = FormularioReservaFragmentDirections.actionFormularioReservaFragmentToFormularioReservaDosFragment3(reserva)
                v.findNavController().navigate(action2)
            }
        }

        val cal = Calendar.getInstance()

        imgBtnCalendar.setOnClickListener() {
            etFecha.error = null

            val mDate = Calendar.DATE
            val mMonth = Calendar.MONTH
            val mYear: Int = Calendar.YEAR

            var datePicker = DatePickerDialog(
                v.context,
                android.R.style.Theme_DeviceDefault_Dialog,
                DatePickerDialog.OnDateSetListener() { _, year, month, date ->

                    lateinit var strDate: String
                    lateinit var strMonth: String
                    var realMonth = month + 1
                    if (date < 10) {
                        strDate = "0${date}"
                    } else {
                        strDate = date.toString()
                    }

                    if (realMonth < 10) {
                        strMonth = "0${realMonth}"
                    } else {
                        strMonth = realMonth.toString()
                    }
                    etFecha.setText("${strDate}/${strMonth}/${year}")
                },
                cal.get(mYear),
                cal.get(mMonth),
                cal.get(mDate)
            )
            datePicker.show()
        }


        imgBtnHour.setOnClickListener(){
            etHora.error = null
            var mHour = Calendar.HOUR
            var mMinute = Calendar.MINUTE

            var datePicker = TimePickerDialog(v.context,android.R.style.Theme_DeviceDefault_Dialog, TimePickerDialog.OnTimeSetListener(){
                    _, hour, minute ->

                lateinit var strMinute : String
                if(minute<10){
                    strMinute = "0${minute}"
                }else{
                    strMinute = minute.toString()
                }

                etHora.setText("${hour}:${strMinute}")

            },cal.get(mHour),cal.get(mMinute),true)
            datePicker.show()
        }
    }
    private fun validar(): Boolean {
        var valido = true
        clearErrors()
        try {
            validarFormatoFecha(etFecha.text.toString())
            validarFecha(etFecha.text.toString())
        } catch (e: Error) {
            etFecha.setError(e.message)
            valido = false
        }

        try {
            validarFormatoHora(etHora.text.toString())
        } catch (e: Error) {
            etHora.setError(e.message)
            valido = false
        }

        try {
            valirdarNoEsNulo(etCantComensales.text.toString())
            validarFormatoNumero(etCantComensales.text.toString())
            cantidadComensalesValida(etCantComensales.text.toString().toInt())
        } catch (e: Error) {
            etCantComensales.setError(e.message)
            valido = false
        }

        try {
            validarString(etDireccion.text.toString())
        } catch (e: Error) {
            etDireccion.setError(e.message)
            valido = false
        }

        return valido
    }

    private fun clearErrors(){
        etFecha.error = null
        etHora.error = null
        etCantComensales.error = null
        etDireccion.error = null
    }
}