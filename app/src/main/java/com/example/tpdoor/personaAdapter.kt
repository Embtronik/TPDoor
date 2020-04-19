package com.example.tpdoor

import android.content.Context
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.persona.view.*

class personaAdapter(private val mContext: Context, private val listpersona: List<persona>) : ArrayAdapter<persona>(mContext,0,listpersona)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        return super.getView(position, convertView, parent)
        val layout = from(mContext).inflate(R.layout.persona,parent,false)
        val pers = listpersona[position]
        layout.txtEmailPersona.text=pers.email
        layout.txtNombre.text=pers.nombre
        return layout
    }
}