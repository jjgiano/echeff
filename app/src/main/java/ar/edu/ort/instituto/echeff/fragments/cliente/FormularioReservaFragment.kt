package ar.edu.ort.instituto.echeff.fragments.cliente

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.time.Month
import java.time.Year
import java.util.*

class FormularioReservaFragment : Fragment() {

    val db = Firebase.firestore

    lateinit var v : View

    lateinit var etFecha : EditText
    lateinit var etHora : EditText
    lateinit var etCantComensales : EditText
    lateinit var etDireccion : EditText
    lateinit var spinnerTipoCocina : Spinner
    lateinit var cbTengoHorno : CheckBox
    lateinit var btnContinuar : Button
    lateinit var imgBtnCalendar : ImageButton
    lateinit var imgBtnHour : ImageButton

    val cal : Calendar = Calendar.getInstance()


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
            var stringFecha = etFecha.text.toString()
            var stringHora = etHora.text.toString()
            var cantComensales = etCantComensales.text.toString().toInt()
            var stringTipoCocina = spinnerTipoCocina.selectedItem.toString()
            var stringDireccion = etDireccion.text.toString()
            var boolTieneHorno = cbTengoHorno.isChecked.toString()

            var reserva = Reserva("", stringFecha,stringHora,stringDireccion,stringTipoCocina,boolTieneHorno.toString(), cantComensales, "","","",1,1)

            val action2 = FormularioReservaFragmentDirections.actionFormularioReservaFragmentToFormularioReservaDosFragment3(reserva)
            v.findNavController().navigate(action2)
        }

        imgBtnCalendar.setOnClickListener(){

            var mDate = Calendar.DATE
            var mMonth = Calendar.MONTH
            var mYear: Int = Calendar.YEAR

            var datePicker = DatePickerDialog(v.context,android.R.style.Theme_DeviceDefault_Dialog, DatePickerDialog.OnDateSetListener(){
                datePicker, year, month, date ->
                lateinit var strDate : String
                if(date<10){
                    strDate = "0${date}"
                }else{
                    strDate = date.toString()
                }
                etFecha.setText("${strDate}/${month+1}/${year}")

            },cal.get(mDate),cal.get(mMonth),cal.get(mYear))
            datePicker.updateDate(2020,mMonth,mDate)
            datePicker.show()
        }

        imgBtnHour.setOnClickListener(){

            var mHour = Calendar.HOUR
            var mMinute = Calendar.MINUTE

            var datePicker = TimePickerDialog(v.context,android.R.style.Theme_DeviceDefault_Dialog, TimePickerDialog.OnTimeSetListener(){
                timePicker, hour, minute ->

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
}