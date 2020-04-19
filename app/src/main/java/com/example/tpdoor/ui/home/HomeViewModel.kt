package com.example.tpdoor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Lista de Dispositivos y Lugares"
    }
    val text: LiveData<String> = _text
}