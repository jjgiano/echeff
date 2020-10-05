package ar.edu.ort.instituto.echeff.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Historia
import com.bumptech.glide.Glide

class HistoriasListAdapter(
    private var historiasList: MutableList<Historia>,
    private val context: Context
) : RecyclerView.Adapter<HistoriasListAdapter.HistoriaHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriaHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_historia, parent, false)
        return HistoriaHolder(view)
    }

    override fun onBindViewHolder(holder: HistoriaHolder, position: Int) {
        Glide
            .with(context)
            .load(historiasList[position].urlImagen)
            .into(holder.getImagenHistoriaView())
        holder.setComentarioHistoria(historiasList[position].comentario)
        holder.setCantidadMegusta(historiasList[position].cantidadMegusta)
    }

    override fun getItemCount(): Int {
        return historiasList.size
    }

    class HistoriaHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun getImagenHistoriaView(): ImageView {
            return view.findViewById(R.id.imgHistoria)
        }

        fun setComentarioHistoria(comentario: String) {
            val comentarioHistoria: TextView = view.findViewById(R.id.txtComentarioHistoria)
            comentarioHistoria.text = comentario
        }

        fun setCantidadMegusta(califiacion: Int) {
            val megustaHistoriaCliente: TextView = view.findViewById(R.id.txtCantidadLikeHistoria)
            megustaHistoriaCliente.text =
                view.context.getString(R.string.cantidad_megusta_chef, califiacion);
        }
    }
}