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
import com.bumptech.glide.Glide

class VistaReservasAdapter(
    private var reservas: MutableList<Reserva>,
    var context: Context,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<VistaReservasAdapter.ReservaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return (ReservaHolder(view))
    }

    override fun getItemCount(): Int {
        return reservas.size
    }

    fun setData(newData: ArrayList<Reserva>) {
        this.reservas = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ReservaHolder, position: Int) {
        Glide
            .with(context)
            .load("https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/12_avatar-256.png")
            .centerInside()
            .into(holder.getImageView());

        holder.setDireccion(StringBuilder().append(reservas[position].direccion).toString())
        holder.setNroComensales(reservas[position].comensales)
        holder.setEstiloComida(StringBuilder().append(reservas[position].estiloCocina).toString())

        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }

    }

    class ReservaHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setDireccion(direccion: String) {
            val tv: TextView = view.findViewById(R.id.text_DatoUsuario)
            tv.text = direccion
        }

        fun setNroComensales(nroComensales: Int) {
            val tv: TextView = view.findViewById(R.id.text_DatosComensales)
            tv.text = StringBuilder().append(nroComensales).append(" Comensal/es").toString()
        }

        fun setEstiloComida(estiloComida: String) {
            val tv: TextView = view.findViewById(R.id.text_DatosEstiloComida)
            tv.text = estiloComida
        }

        fun getCardLayout(): CardView {
            return view.findViewById(R.id.card_itemsReserva)
        }

        fun getImageView(): ImageView {
            return view.findViewById(R.id.imageViewChef)
        }

    }

}