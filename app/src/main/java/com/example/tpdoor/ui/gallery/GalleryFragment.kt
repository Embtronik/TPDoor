package com.example.tpdoor.ui.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tpdoor.R
import com.example.tpdoor.persona
import com.example.tpdoor.personaAdapter

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)

        val txtNombre: TextView = root.findViewById(R.id.txtNombreUsuario)
        val txtPass: TextView = root.findViewById(R.id.txtPassUsuario)
        val txtEmail: TextView = root.findViewById(R.id.txtEmailUsuario)

        val btnRegistrar: Button=root.findViewById(R.id.btnAgregarUsuario)
        val listaPer: ListView = root.findViewById(R.id.listUser)

        var listaPersona: MutableList<persona> = mutableListOf()

        btnRegistrar.setOnClickListener()
        {

            if (TextUtils.isEmpty(txtNombre.text.toString().trim())) {
                Toast.makeText(getActivity(),"Se debe ingresar un Nombre Valido",Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(txtPass.text.toString().trim())) {
            Toast.makeText(getActivity(), "Se debe ingresar un Password Valido", Toast.LENGTH_LONG).show();
            return@setOnClickListener
            }
            if (TextUtils.isEmpty(txtEmail.text.toString().trim())) {
                Toast.makeText(getActivity(), "Se debe ingresar un Email Valido", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }

            val user = persona(txtNombre.text.toString().trim(),txtEmail.text.toString().trim(),txtPass.text.toString().trim(),"")
            listaPersona.add(user);
            val adaptador = personaAdapter(root.context,listaPersona)
            listaPer.adapter = adaptador

            txtNombre.setText("");
            txtEmail.setText("");
            txtPass.setText("");

        }

        listaPer.setOnItemClickListener { parent, view, position, id ->

            val builder = AlertDialog.Builder(root.context)
            builder.setTitle("Eliminar Usuario")
            builder.setMessage("Desea Borrar Usuario")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(root.context,"Se Eliminó el Usuario", Toast.LENGTH_SHORT).show()
                listaPersona.removeAt(position)
                val adaptador = personaAdapter(root.context,listaPersona)
                listaPer.adapter = adaptador
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(root.context,"No se Eliminó el Usuario", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
