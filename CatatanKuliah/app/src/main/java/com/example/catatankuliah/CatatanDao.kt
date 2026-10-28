package com.example.catatankuliah

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatatanDao {

    @Query("SELECT * FROM catatan ORDER BY id DESC")
    fun ambilSemuaCatatan(): Flow<List<Catatan>>

    @Insert
    suspend fun tambahCatatan(catatan: Catatan)

    @Update
    suspend fun ubahCatatan(catatan: Catatan)

    @Delete
    suspend fun hapusCatatan(catatan: Catatan)
}