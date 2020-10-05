package ar.edu.ort.instituto.echeff.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.instituto.echeff.R
import ar.edu.ort.instituto.echeff.entities.Comentario
import com.bumptech.glide.Glide

class ComentariosListAdapter(
    private var comentarioList: MutableList<Comentario>,
    private val context: Context
) : RecyclerView.Adapter<ComentariosListAdapter.ComentarioHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)

        return ComentarioHolder(view);
    }

    override fun onBindViewHolder(holder: ComentarioHolder, position: Int) {
        holder.setComentario(comentarioList[position].comentario)
        holder.setCalificacion(comentarioList[position].califiacion)

        Glide
            .with(context)
            .load(comentarioList[position].urlAvatar)
            .centerCrop()
            .into(holder.getAvatarView())
    }

    override fun getItemCount(): Int {
        return comentarioList.size
    }


    class ComentarioHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun setComentario(comentario: String) {
            val comentarioCliente: TextView = view.findViewById(R.id.txtComentarioCliente)
            comentarioCliente.text = comentario
        }

        fun setCalificacion(califiacion: Int) {
            val calificaiconCliente: TextView = view.findViewById(R.id.txtCalificacionCliente)
            calificaiconCliente.text = view.context.getString(R.string.comentario_puntuacion_cliente, califiacion);
        }

        fun getAvatarView(): ImageView {
            return view.findViewById(R.id.imgHistoria)
        }
    }
}

