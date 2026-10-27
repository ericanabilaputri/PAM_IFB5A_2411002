
package com.example.manajementugasmahasiswa.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manajementugasmahasiswa.data.TugasEntity

@Composable
fun DetailTugasScreen(
    tugas: TugasEntity,
    onKembali: () -> Unit,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FF))
            .padding(20.dp)
    ) {
        Text(
            text = "Detail Tugas",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF17234D)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                KolomDetail("Judul Tugas", tugas.judul)
                KolomDetail("Mata Kuliah", tugas.mataKuliah)
                KolomDetail("Deadline", tugas.deadline)
                KolomDetail("Status", tugas.status)
                KolomDetail(
                    "Deskripsi",
                    tugas.deskripsi.ifBlank { "Tidak ada deskripsi" }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onKembali,
                modifier = Modifier.weight(1f)
            ) {
                Text("Kembali")
            }

            Button(
                onClick = onEdit,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6554F0)
                )
            ) {
                Text("Edit Tugas")
            }
        }
    }
}

@Composable
private fun KolomDetail(
    label: String,
    isi: String
) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = isi,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF17234D)
        )
    }
}