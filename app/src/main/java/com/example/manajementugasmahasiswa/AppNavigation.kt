
package com.example.manajementugasmahasiswa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manajementugasmahasiswa.data.TugasEntity
import com.example.manajementugasmahasiswa.ui.TugasViewModel
import com.example.manajementugasmahasiswa.ui.EditTugasScreen

@Composable
fun AppNavigation(viewModel: TugasViewModel) {

    val navController = rememberNavController()
    val daftarTugas by viewModel.daftarTugas.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "daftar"
    ) {
        composable("daftar") {
            HalamanDaftarTugas(
                viewModel = viewModel,
                onBukaDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }

        composable(
            route = "detail/{tugasId}",
            arguments = listOf(
                navArgument("tugasId") {
                    type = NavType.IntType
                }
            )
        ) { entry ->

            val tugasId = entry.arguments?.getInt("tugasId")
            val tugas = daftarTugas.find { it.id == tugasId }

            if (tugas != null) {
                HalamanDetailNavigasi(
                    tugas = tugas,
                    onKembali = {
                        navController.popBackStack()
                    },
                    onEdit = {
                        navController.navigate("edit/${tugas.id}")
                    }
                )
            }
        }

        composable(
            route = "edit/{tugasId}",
            arguments = listOf(
                navArgument("tugasId") {
                    type = NavType.IntType
                }
            )
        ) { entry ->

            val tugasId = entry.arguments?.getInt("tugasId")
            val tugas = daftarTugas.find { it.id == tugasId }

            if (tugas != null) {
                HalamanEditNavigasi(
                    tugas = tugas,
                    onKembali = {
                        navController.popBackStack()
                    },
                    onSimpan = { tugasBaru ->
                        viewModel.ubahTugas(tugasBaru)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun HalamanDetailNavigasi(
    tugas: TugasEntity,
    onKembali: () -> Unit,
    onEdit: () -> Unit
) {
    com.example.manajementugasmahasiswa.ui.DetailTugasScreen(
        tugas = tugas,
        onKembali = onKembali,
        onEdit = onEdit
    )
}

@Composable
private fun HalamanEditNavigasi(
    tugas: TugasEntity,
    onKembali: () -> Unit,
    onSimpan: (TugasEntity) -> Unit
) {
    EditTugasScreen(
        tugas = tugas,
        onKembali = onKembali,
        onSimpan = onSimpan
    )
}