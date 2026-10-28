
package com.example.profilnavigasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AplikasiProfil()
            }
        }
    }
}

@Composable
fun AplikasiProfil(vm: ProfilViewModel = viewModel()) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "beranda"
    ) {
        composable("beranda") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Beranda",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(16.dp))
                Text("Selamat datang di Aplikasi Profil Mahasiswa.")
                Button(
                    onClick = { nav.navigate("profil") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Buka Profil")
                }
            }
        }

        composable("profil") {
            LayarProfil(nav, vm)
        }

        composable("matakuliah") {
            LayarMataKuliah(nav, vm)
        }
    }
}

@Composable
fun LayarProfil(nav: NavHostController, vm: ProfilViewModel) {
    var nama by rememberSaveable { mutableStateOf(vm.nama) }
    var nim by rememberSaveable { mutableStateOf(vm.nim) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Profil Mahasiswa",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                vm.simpan(nama, nim)
                nav.navigate("matakuliah")
            },
            enabled = nama.isNotBlank() && nim.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan dan Lihat Detail")
        }

        Button(
            onClick = { nav.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali")
        }
    }
}

@Composable
fun LayarMataKuliah(nav: NavHostController, vm: ProfilViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Detail Mata Kuliah",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Data Profil Mahasiswa")
                Text("Nama: ${vm.nama}")
                Text("NIM: ${vm.nim}")
                Text("Program Studi: Informatika")
            }
        }

        Text("Daftar Mata Kuliah")
        Text("1. Pemrograman Aplikasi Bergerak")
        Text("2. Basis Data")
        Text("3. Rekayasa Perangkat Lunak")

        Button(
            onClick = { nav.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali ke Profil")
        }

        Button(
            onClick = {
                nav.popBackStack("beranda", inclusive = false)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali ke Beranda")
        }
    }
}