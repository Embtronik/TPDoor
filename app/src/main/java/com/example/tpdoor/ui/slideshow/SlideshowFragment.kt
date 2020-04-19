package com.example.tpdoor.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tpdoor.DeviceAdapter
import com.example.tpdoor.DeviceIoT
import com.example.tpdoor.R
import com.example.tpdoor.ui.home.HomeFragment
import kotlinx.android.synthetic.main.device.*

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        if(arguments!=null)
        {
            val strtext = arguments!!.getString("iKey")
            Toast.makeText(root.context,"Slide: "+strtext,Toast.LENGTH_LONG).show()
            /*var comingData = this!!.arguments!!.getString("iKey")*/
        }

        val textView: TextView = root.findViewById(R.id.text_slideshow)
        val textSerial: EditText = root.findViewById(R.id.txtSerial)
        val textLugar : EditText = root.findViewById(R.id.txtLugar)

        val btnAgregar : Button = root.findViewById(R.id.btnAgregar)
        val btnGuardar : Button = root.findViewById(R.id.btnGuardar)
        val listaDev : ListView = root.findViewById(R.id.lista)

        var listaDeviceIoT: MutableList<DeviceIoT> = mutableListOf()

        btnAgregar.setOnClickListener()
        {
            if(TextUtils.isEmpty(textSerial.text.toString().trim()))
            {
                Toast.makeText(getActivity(),"Se debe ingresar un Serial Valido", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(textLugar.text.toString().trim()))
            {
                Toast.makeText(getActivity(),"Se debe ingresar un Lugar Valido", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }

            val device = DeviceIoT(textSerial.text.toString().trim(),textLugar.text.toString().trim())
            listaDeviceIoT.add(device)
            val adapter = DeviceAdapter(root.context,listaDeviceIoT)
            listaDev.adapter = adapter

            textLugar.setText("");
            txtSerial.setText("");
        }

        //Con esta función elimino el ítem de la lista seleccionado
        listaDev.setOnItemClickListener { parent, view, position, id ->

            val builder = AlertDialog.Builder(root.context) //esto es lo que
            builder.setTitle("Eliminar Dispositivo")
            builder.setMessage("Desea Eliminar el Dispositivo Seleccionado?...")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(root.context, "Se Eliminó el Dispositivo", Toast.LENGTH_SHORT).show()
                listaDeviceIoT.removeAt(position)
                val adapter = DeviceAdapter(root.context, listaDeviceIoT)
                listaDev.adapter = adapter
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(root.context,"No se Eliminó el Dispositivo", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}
