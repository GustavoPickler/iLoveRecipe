package com.example.iluvrecipe.controller.adapter

import android.content.Intent
import android.os.Build
import com.example.iluvrecipe.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.iluvrecipe.model.Receita
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.iluvrecipe.controller.CadastroReceita

class LineAdapter(private val listaReceitas: List<Receita>) : RecyclerView.Adapter<LineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lista_receita_item, parent, false))

        viewHolder.container.setOnClickListener {
            val receita : Receita = listaReceitas[viewHolder.adapterPosition]
            val intent = Intent(parent.context, CadastroReceita::class.java)
            intent.putExtra("receita", receita)
            ContextCompat.startActivity(parent.context, intent, null)
        }

        return viewHolder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receita = listaReceitas[position]
        holder.title.text = receita.nome
        holder.descricao.text = receita.descricao
        if (receita.image == "") {
            holder.imagem.visibility = View.GONE
        } else {
            val byteArray = Base64.getDecoder().decode(receita.image);
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            holder.imagem.setImageBitmap(bmp)
        }
    }

    override fun getItemCount(): Int {
        return listaReceitas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tituloReceita)
        val descricao: TextView = itemView.findViewById(R.id.descricaoReceita)
        val imagem: ImageView = itemView.findViewById(R.id.imagemReceita)
        val container : LinearLayout = itemView.findViewById(R.id.containerReceitaItem)
    }
}
