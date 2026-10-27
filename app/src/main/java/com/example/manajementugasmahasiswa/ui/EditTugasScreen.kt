
package com.example.manajementugasmahasiswa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manajementugasmahasiswa.data.TugasEntity

@Composable
fun EditTugasScreen(
    tugas: TugasEntity,
    onKembali: () -> Unit,
    onSimpan: (TugasEntity) -> Unit
) {
    var judul by remember(tugas.id) {
        mutableStateOf(tugas.judul)
    }

    var mataKuliah by remember(tugas.id) {
        mutableStateOf(tugas.mataKuliah)
    }

    var deadline by remember(tugas.id) {
        mutableStateOf(tugas.deadline)
    }

    var deskripsi by remember(tugas.id) {
        mutableStateOf(tugas.deskripsi)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Edit Tugas")

        OutlinedTextField(
            value = judul,
            onValueChange = { judul = it },
            label = { Text("Judul tugas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = mataKuliah,
            onValueChange = { mataKuliah = it },
            label = { Text("Mata kuliah") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deadline,
            onValueChange = { deadline = it },
            label = { Text("Deadline") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = { deskripsi = it },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (
                    judul.isNotBlank() &&
                    mataKuliah.isNotBlank()
                ) {
                    onSimpan(
                        tugas.copy(
                            judul = judul.trim(),
                            mataKuliah = mataKuliah.trim(),
                            deadline = deadline
                                .ifBlank { "Belum ditentukan" }
                                .trim(),
                            deskripsi = deskripsi.trim()
                        )
                    )
                }
            },
            enabled = judul.isNotBlank() &&
                    mataKuliah.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Perubahan")
        }

        OutlinedButton(
            onClick = onKembali,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Batal")
        }
    }
}