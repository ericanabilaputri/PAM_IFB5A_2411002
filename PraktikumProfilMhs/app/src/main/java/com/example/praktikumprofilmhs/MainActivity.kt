
package com.example.praktikumprofilmhs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormProfil(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FormProfil(modifier: Modifier = Modifier) {
    var nama by rememberSaveable { mutableStateOf("") }
    var nim by rememberSaveable { mutableStateOf("") }
    var prodi by rememberSaveable { mutableStateOf("") }
    var sudahSimpan by rememberSaveable { mutableStateOf(false) }

    val valid = nama.isNotBlank() &&
            nim.isNotBlank() &&
            prodi.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Form Profil Mahasiswa",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = nama,
            onValueChange = {
                nama = it
                sudahSimpan = false
            },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = nim,
            onValueChange = {
                nim = it
                sudahSimpan = false
            },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            value = prodi,
            onValueChange = {
                prodi = it
                sudahSimpan = false
            },
            label = { Text("Program Studi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (!valid) {
            Text("Lengkapi ketiga kolom untuk menyimpan.")
        }

        Button(
            onClick = { sudahSimpan = true },
            enabled = valid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Profil")
        }

        if (sudahSimpan) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Profil Tersimpan",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Nama: $nama")
                    Text("NIM: $nim")
                    Text("Program Studi: $prodi")
                }
            }
        }
    }
}