package ar.edu.ort.instituto.echeff.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Reserva

class adapterListReserva (private var reservaList : MutableList<Reserva>, var context: Context, val onItemClick : (Int) -> Unit) : RecyclerView.Adapter<adapterListReserva.ReservaHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_reserva,parent,false)
        return (ReservaHolder(view))
    }

    override fun getItemCount(): Int {

        return reservaList.size
    }

    fun setData(newData: ArrayList<Reserva>) {
        this.reservaList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ReservaHolder, position: Int) {

        holder.setDireccion(reservaList[position].direccion)
        holder.setComensales(reservaList[position].comensales)
        holder.setEstiloComida(reservaList[position].estiloCocina)
        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }
       /* Glide
            .with(context)
            .load(reservaList[position].imagen)
            .into(holder.getImageView());
            .centerInside()
        */
        holder.getCardLayout().setOnClickListener {
            onItemClick(position)


        }

    }

    class ReservaHolder (v: View) : RecyclerView.ViewHolder(v){

        private var view: View

        init {
            this.view = v
        }

        fun setDireccion(name : String) {
            val txt : TextView = view.findViewById(R.id.text_DatoUsuario)
            txt.text = name
        }
        fun setComensales(cantidad : Int) {
            val txt : TextView = view.findViewById(R.id.text_DatosComensales)
            txt.text = cantidad.toString()
        }
        fun setEstiloComida(name : String) {
            val txt : TextView = view.findViewById(R.id.text_DatosEstiloComida)
            txt.text = name
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_itemsReserva)
        }

        fun getImageView () : ImageView {
            return view.findViewById(R.id.imageViewChef)
        }

    }
}