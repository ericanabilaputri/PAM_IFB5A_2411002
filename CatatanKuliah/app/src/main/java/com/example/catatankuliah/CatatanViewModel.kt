package com.example.catatankuliah

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CatatanViewModel(application: Application) :
    AndroidViewModel(application) {

    private val dao = CatatanDatabase
        .getDatabase(application)
        .catatanDao()

    val daftarCatatan = dao.ambilSemuaCatatan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun tambah(judul: String, isi: String) {
        if (judul.isBlank() || isi.isBlank()) return

        viewModelScope.launch {
            dao.tambahCatatan(
                Catatan(
                    judul = judul.trim(),
                    isi = isi.trim()
                )
            )
        }
    }

    fun ubah(catatan: Catatan, judul: String, isi: String) {
        if (judul.isBlank() || isi.isBlank()) return

        viewModelScope.launch {
            dao.ubahCatatan(
                catatan.copy(
                    judul = judul.trim(),
                    isi = isi.trim()
                )
            )
        }
    }

    fun hapus(catatan: Catatan) {
        viewModelScope.launch {
            dao.hapusCatatan(catatan)
        }
    }
}