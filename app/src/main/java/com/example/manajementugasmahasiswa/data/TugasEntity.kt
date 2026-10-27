package com.example.manajementugasmahasiswa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tugas")
data class TugasEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val mataKuliah: String,
    val deadline: String,
    val deskripsi: String,
    val status: String = "Belum"
)