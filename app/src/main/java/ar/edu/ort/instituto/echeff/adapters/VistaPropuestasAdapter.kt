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
import ar.edu.ort.instituto.echeff.entities.Propuesta
import com.bumptech.glide.Glide

class VistaPropuestasAdapter(
    private var propuestas: MutableList<Propuesta>,
    var context: Context,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<VistaPropuestasAdapter.PropuestaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropuestaHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_propuesta, parent, false)
        return (PropuestaHolder(view))
    }

    override fun getItemCount(): Int {
        return propuestas.size
    }

    fun setData(newData: ArrayList<Propuesta>) {
        this.propuestas = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PropuestaHolder, position: Int) {
        Glide
            .with(context)
            .load(propuestas[position].urlImg)
            .circleCrop()
            .into(holder.getImageView());

        holder.setDireccion(StringBuilder().append(propuestas[position].total).toString())
        holder.setEstiloComida(StringBuilder().append(propuestas[position].snack).toString())

        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }

    }

    class PropuestaHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setDireccion(direccion: String) {
            val tv: TextView = view.findViewById(R.id.tvDireccion)
            tv.text = direccion
        }

        fun setEstiloComida(estiloComida: String) {
            val tv: TextView = view.findViewById(R.id.tvEstiloComida)
            tv.text = view.context.getString(R.string.estilo_comida_home_cliente, estiloComida);
        }

        fun getCardLayout(): CardView {
            return view.findViewById(R.id.cvItemPropuesta)
        }

        fun getImageView(): ImageView {
            return view.findViewById(R.id.ivChef)
        }



    }

}