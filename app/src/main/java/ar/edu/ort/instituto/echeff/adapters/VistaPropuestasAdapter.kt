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
            .load("https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/2_avatar-256.png")
            .centerInside()
            .into(holder.getImageView());

        holder.setPlatoPrincipal(StringBuilder().append(propuestas[position].plato).toString())
        holder.setEntrada(StringBuilder().append(propuestas[position].entrada).toString())
        holder.setPostre(StringBuilder().append(propuestas[position].postre).toString())
        holder.setSnack(StringBuilder().append(propuestas[position].snack).toString())


        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }

    }

    class PropuestaHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setPlatoPrincipal(plato: String) {
            val tv: TextView = view.findViewById(R.id.txtNombrePlatoPrincipal)
            tv.text = plato
        }

        fun setEntrada(entrada: String) {
            val tv: TextView = view.findViewById(R.id.txtNombreEntrada)
            tv.text = view.context.getString(R.string.nombre_entrada, entrada);
        }

        fun setPostre(postre: String) {
                 val tv: TextView = view.findViewById(R.id.txtNombrePostre)
                 tv.text = view.context.getString(R.string.nombre_postre, postre);
        }

        fun setSnack(snack: String) {
            val tv: TextView = view.findViewById(R.id.txtNombreSnack)
            tv.text = view.context.getString(R.string.nombre_snack, snack);
        }

        fun getCardLayout(): CardView {
            return view.findViewById(R.id.cvItemPropuesta)
        }

        fun getImageView(): ImageView {
            return view.findViewById(R.id.imageViewChef)
        }



    }

}