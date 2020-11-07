package ar.edu.ort.instituto.echeff.validator

import android.text.TextUtils
import java.lang.Error
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.Calendar.*
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

interface Validator{

    val CANT_MAX_COMENSALES : Int
        get() = 10
    val DIAS_MINIMOS_PARA_SERVICIO : Int
        get() = 3

    fun validarEmail(s : String) : Boolean{
        return !TextUtils.isEmpty(this.toString()) && android.util.Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()
    }

    fun validarFormatoFecha(s : String){
        var formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            formatter.parse(s)
        }catch (e : ParseException){
            throw Error("Formato de fecha no valido")
        }
    }


    fun validarFecha(s : String){

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = formatter.parse(s) ?: Date()

        val d = Calendar.getInstance()
        d.add(Calendar.DAY_OF_YEAR,DIAS_MINIMOS_PARA_SERVICIO)

        val date2 = d.time

        if (date.before(date2)){
            throw Error("Fecha menor a $DIAS_MINIMOS_PARA_SERVICIO dias para servicio")
        }
    }

    fun validarFormatoHora(s : String){
        var formatter = SimpleDateFormat("hh:mm", Locale.getDefault())
        try {
            formatter.parse(s)
        }catch (e : ParseException){
            throw Error("Formato de hora no valido")
        }
    }

    fun validarString(s : String){
        if (s == ""){
            throw Error("Campo no puede estar vacio")
        }
    }

    fun validarFormatoNumero(s : String){
        try{
            s.toInt()
        }catch (e : NumberFormatException){
            throw Error("Formato invalido")
        }
    }

    fun cantidadComensalesValida(i : Int){
        if (!(i in 1..CANT_MAX_COMENSALES)){
            throw Error("Cantidad de comensales invalida. Ingrese entre 1 y ${CANT_MAX_COMENSALES}")
        }
    }

    fun valirdarNoEsNulo(s: String){
        if(s.isNullOrEmpty()){
            throw Error("No puede estar vacio. Ingrese entre 1 y ${CANT_MAX_COMENSALES}")
        }
    }

}