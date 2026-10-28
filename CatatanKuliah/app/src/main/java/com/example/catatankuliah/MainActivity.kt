
package com.example.catatankuliah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CatatanKuliahApp()
            }
        }
    }
}

@Composable
fun CatatanKuliahApp(
    vm: CatatanViewModel = viewModel()
) {
    val daftarCatatan by vm.daftarCatatan.collectAsState()

    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }
    var catatanDipilih by remember { mutableStateOf<Catatan?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Catatan Kuliah",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (catatanDipilih == null)
                    "Tambah catatan baru"
                else
                    "Edit catatan",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                label = { Text("Judul catatan") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = isi,
                onValueChange = { isi = it },
                label = { Text("Isi catatan") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (
                            judul.isNotBlank() &&
                            isi.isNotBlank()
                        ) {
                            val terpilih = catatanDipilih

                            if (terpilih == null) {
                                vm.tambah(judul, isi)
                            } else {
                                vm.ubah(terpilih, judul, isi)
                            }

                            judul = ""
                            isi = ""
                            catatanDipilih = null
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        if (catatanDipilih == null)
                            "Simpan"
                        else
                            "Perbarui"
                    )
                }

                if (catatanDipilih != null) {
                    OutlinedButton(
                        onClick = {
                            judul = ""
                            isi = ""
                            catatanDipilih = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Batal")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Daftar Catatan",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (daftarCatatan.isEmpty()) {
                Text("Belum ada catatan. Silakan tambahkan catatan.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = daftarCatatan,
                        key = { it.id }
                    ) { catatan ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = catatan.judul,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(text = catatan.isi)

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Row(
                                    horizontalArrangement =
                                        Arrangement.spacedBy(8.dp)
                                ) {
                                    TextButton(
                                        onClick = {
                                            catatanDipilih = catatan
                                            judul = catatan.judul
                                            isi = catatan.isi
                                        }
                                    ) {
                                        Text("Edit")
                                    }

                                    TextButton(
                                        onClick = {
                                            vm.hapus(catatan)

                                            if (
                                                catatanDipilih?.id ==
                                                catatan.id
                                            ) {
                                                catatanDipilih = null
                                                judul = ""
                                                isi = ""
                                            }
                                        }
                                    ) {
                                        Text("Hapus")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}