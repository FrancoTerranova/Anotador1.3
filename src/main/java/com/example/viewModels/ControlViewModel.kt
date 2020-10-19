package com.example.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ControlViewModel : ViewModel() {
    val navegar : MutableLiveData<Boolean> = MutableLiveData()
    val idBoton : MutableLiveData<String> = MutableLiveData()
    val accion : MutableLiveData<String> = MutableLiveData()
    val causaError : MutableLiveData<String> = MutableLiveData("")
    val origenError : MutableLiveData<String> = MutableLiveData("")
    val error : MutableLiveData<Boolean> = MutableLiveData(false)
    fun iniciarDatos(){
        navegar.value = false
        idBoton.value = ""
        accion.value = ""

    }
    fun limpiarError(){
        error.value = false
        causaError.value = ""
        origenError.value = ""
    }
    fun setError(causa : String,origen : String, iserror : Boolean){
        error.value = iserror
        causaError.value = causa
        origenError.value = origen
    }
    fun setNavegar(n : Boolean){
        navegar.value = n
    }
    fun setAccion(a : String){
        accion.value = a
    }
    fun setidButton(s : String){
        idBoton.value = s
    }
    fun Navegar(n : Boolean, id : String){
        setNavegar(n)
        setidButton(id)
    }

}