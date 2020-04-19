package com.example.tpdoor

import android.content.Context
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.device.view.*

class DeviceAdapter(private val mContext: Context, private val listaDeviceIoT: List<DeviceIoT>) : ArrayAdapter<DeviceIoT>(mContext,0,listaDeviceIoT) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = from(mContext).inflate(R.layout.device,parent,false)
        val dev = listaDeviceIoT[position]
        layout.txtSerial.text = dev.serial
        layout.txtLugar.text=dev.lugar
        return layout
        //return super.getView(position, convertView, parent)
    }
}