package com.example.manajementugasmahasiswa.data

import kotlinx.coroutines.flow.Flow

class TugasRepository(
    private val tugasDao: TugasDao
) {

    fun getAllTugas(): Flow<List<TugasEntity>> {
        return tugasDao.getAllTugas()
    }

    fun getTugasById(id: Int): Flow<TugasEntity?> {
        return tugasDao.getTugasById(id)
    }

    fun getJumlahTugas(): Flow<Int> {
        return tugasDao.getJumlahTugas()
    }

    suspend fun insertTugas(tugas: TugasEntity) {
        tugasDao.insertTugas(tugas)
    }

    suspend fun updateTugas(tugas: TugasEntity) {
        tugasDao.updateTugas(tugas)
    }

    suspend fun deleteTugas(tugas: TugasEntity) {
        tugasDao.deleteTugas(tugas)
    }

    suspend fun deleteTugasById(id: Int) {
        tugasDao.deleteTugasById(id)
    }
}