package com.example.profilnavigasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfilViewModel : ViewModel() {

    var nama by mutableStateOf("")
        private set

    var nim by mutableStateOf("")
        private set

    fun simpan(namaBaru: String, nimBaru: String) {
        nama = namaBaru
        nim = nimBaru
    }
}