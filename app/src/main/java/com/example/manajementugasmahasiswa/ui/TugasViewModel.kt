package com.example.manajementugasmahasiswa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajementugasmahasiswa.data.TugasEntity
import com.example.manajementugasmahasiswa.data.TugasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TugasViewModel(
    private val repository: TugasRepository
) : ViewModel() {

    val daftarTugas: StateFlow<List<TugasEntity>> =
        repository.getAllTugas().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val jumlahTugas: StateFlow<Int> =
        repository.getJumlahTugas().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun tambahTugas(
        judul: String,
        mataKuliah: String,
        deadline: String,
        deskripsi: String,
        status: String = "Belum"
    ) {
        if (judul.isBlank() || mataKuliah.isBlank()) return

        viewModelScope.launch {
            repository.insertTugas(
                TugasEntity(
                    judul = judul.trim(),
                    mataKuliah = mataKuliah.trim(),
                    deadline = deadline.trim(),
                    deskripsi = deskripsi.trim(),
                    status = status
                )
            )
        }
    }

    fun ubahTugas(tugas: TugasEntity) {
        viewModelScope.launch {
            repository.updateTugas(tugas)
        }
    }

    fun ubahStatus(
        tugas: TugasEntity,
        statusBaru: String
    ) {
        val statusValid = listOf("Belum", "Proses", "Selesai")

        if (statusBaru !in statusValid) return

        viewModelScope.launch {
            repository.updateTugas(
                tugas.copy(status = statusBaru)
            )
        }
    }

    fun hapusTugas(tugas: TugasEntity) {
        viewModelScope.launch {
            repository.deleteTugas(tugas)
        }
    }

    fun hapusTugas(id: Int) {
        viewModelScope.launch {
            repository.deleteTugasById(id)
        }
    }
}