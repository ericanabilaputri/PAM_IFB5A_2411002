
package com.example.manajementugasmahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.manajementugasmahasiswa.data.AppDatabase
import com.example.manajementugasmahasiswa.data.TugasEntity
import com.example.manajementugasmahasiswa.data.TugasRepository
import com.example.manajementugasmahasiswa.ui.TugasViewModel
import com.example.manajementugasmahasiswa.ui.TugasViewModelFactory

private val Biru = Color(0xFF4568F5)
private val Ungu = Color(0xFF6554F0)
private val Latar = Color(0xFFF5F6FF)
private val Teks = Color(0xFF17234D)

class MainActivity : ComponentActivity() {

    private val tugasViewModel: TugasViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = TugasRepository(database.tugasDao())
        TugasViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AppNavigation(viewModel = tugasViewModel)
            }
        }
    }
}

@Composable
fun HalamanDaftarTugas(
    viewModel: TugasViewModel,
    onBukaDetail: (Int) -> Unit
) {
    val daftarTugas by viewModel.daftarTugas.collectAsStateWithLifecycle()
    val jumlahTugas by viewModel.jumlahTugas.collectAsStateWithLifecycle()

    var pencarian by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("Semua") }
    var tampilForm by remember { mutableStateOf(false) }
    var tugasDihapus by remember { mutableStateOf<TugasEntity?>(null) }

    var judul by remember { mutableStateOf("") }
    var mataKuliah by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }

    val tugasTampil = daftarTugas.filter { tugas ->
        (tugas.judul.contains(pencarian, ignoreCase = true) ||
                tugas.mataKuliah.contains(pencarian, ignoreCase = true)) &&
                (filter == "Semua" || tugas.status == filter)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Latar)
            .statusBarsPadding()
            .padding(horizontal = 18.dp)
    ) {
        Spacer(Modifier.height(18.dp))

        Text(
            text = "Halo, Mahasiswa! 👋",
            color = Ungu,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Manajemen Tugas",
            color = Teks,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "Atur dan pantau tugas kuliahmu dengan mudah.",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Statistik(
                label = "Belum",
                jumlah = daftarTugas.count { it.status == "Belum" },
                warna = Color(0xFFFFE8EF),
                modifier = Modifier.weight(1f)
            )

            Statistik(
                label = "Proses",
                jumlah = daftarTugas.count { it.status == "Proses" },
                warna = Color(0xFFE5EDFF),
                modifier = Modifier.weight(1f)
            )

            Statistik(
                label = "Selesai",
                jumlah = daftarTugas.count { it.status == "Selesai" },
                warna = Color(0xFFE0F8EB),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(18.dp))

        OutlinedTextField(
            value = pencarian,
            onValueChange = { pencarian = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Cari tugas atau mata kuliah...") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Semua", "Belum", "Proses", "Selesai").forEach { status ->
                FilterChip(
                    selected = filter == status,
                    onClick = { filter = status },
                    label = { Text(status, fontSize = 11.sp) }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Daftar Tugas",
                color = Teks,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "$jumlahTugas",
                color = Biru,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    judul = ""
                    mataKuliah = ""
                    deadline = ""
                    deskripsi = ""
                    tampilForm = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Biru
                ),
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
            ) {
                Text("+ Tambah", fontSize = 12.sp)
            }
        }

        if (tugasTampil.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada tugas yang sesuai.",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(tugasTampil, key = { it.id }) { tugas ->
                    KartuTugas(
                        tugas = tugas,
                        onDetail = {
                            onBukaDetail(tugas.id)
                        },
                        onStatusChange = {
                            val statusBaru = when (tugas.status) {
                                "Belum" -> "Proses"
                                "Proses" -> "Selesai"
                                else -> "Belum"
                            }
                            viewModel.ubahStatus(tugas, statusBaru)
                        },
                        onDelete = {
                            tugasDihapus = tugas
                        }
                    )
                }
            }
        }
    }

    if (tampilForm) {
        AlertDialog(
            onDismissRequest = { tampilForm = false },
            title = { Text("Tambah Tugas Baru") },
            text = {
                FormTugas(
                    judul = judul,
                    onJudulChange = { judul = it },
                    mataKuliah = mataKuliah,
                    onMataKuliahChange = { mataKuliah = it },
                    deadline = deadline,
                    onDeadlineChange = { deadline = it },
                    deskripsi = deskripsi,
                    onDeskripsiChange = { deskripsi = it }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (judul.isNotBlank() && mataKuliah.isNotBlank()) {
                            viewModel.tambahTugas(
                                judul = judul.trim(),
                                mataKuliah = mataKuliah.trim(),
                                deadline = deadline.ifBlank {
                                    "Belum ditentukan"
                                },
                                deskripsi = deskripsi.trim()
                            )
                            tampilForm = false
                        }
                    },
                    enabled = judul.isNotBlank() && mataKuliah.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Ungu
                    )
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { tampilForm = false }) {
                    Text("Batal")
                }
            }
        )
    }

    tugasDihapus?.let { tugas ->
        AlertDialog(
            onDismissRequest = { tugasDihapus = null },
            title = { Text("Hapus Tugas?") },
            text = {
                Text("Tugas \"${tugas.judul}\" akan dihapus secara permanen.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.hapusTugas(tugas)
                        tugasDihapus = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE83D67)
                    )
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { tugasDihapus = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun FormTugas(
    judul: String,
    onJudulChange: (String) -> Unit,
    mataKuliah: String,
    onMataKuliahChange: (String) -> Unit,
    deadline: String,
    onDeadlineChange: (String) -> Unit,
    deskripsi: String,
    onDeskripsiChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 420.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = judul,
            onValueChange = onJudulChange,
            label = { Text("Judul tugas") },
            singleLine = true
        )

        OutlinedTextField(
            value = mataKuliah,
            onValueChange = onMataKuliahChange,
            label = { Text("Mata kuliah") },
            singleLine = true
        )

        OutlinedTextField(
            value = deadline,
            onValueChange = onDeadlineChange,
            label = { Text("Deadline") },
            placeholder = { Text("Contoh: 30 Oktober 2026") },
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = onDeskripsiChange,
            label = { Text("Deskripsi") }
        )
    }
}

@Composable
fun Statistik(
    label: String,
    jumlah: Int,
    warna: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = warna)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$jumlah",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Teks
            )
            Spacer(Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = Teks)
        }
    }
}

@Composable
fun KartuTugas(
    tugas: TugasEntity,
    onDetail: () -> Unit,
    onStatusChange: () -> Unit,
    onDelete: () -> Unit
) {
    val warnaStatus = when (tugas.status) {
        "Selesai" -> Color(0xFF0DAA72)
        "Proses" -> Biru
        else -> Color(0xFFE83D67)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetail() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            Color(0xFFEAF0FF),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📚", fontSize = 23.sp)
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = tugas.judul,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Teks
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = tugas.mataKuliah,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "📅  ${tugas.deadline}",
                fontSize = 13.sp,
                color = Teks
            )

            if (tugas.deskripsi.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = tugas.deskripsi,
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = warnaStatus.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "● ${tugas.status}",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        color = warnaStatus,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.weight(1f))

                TextButton(onClick = onStatusChange) {
                    Text("Status", fontSize = 11.sp)
                }

                TextButton(onClick = onDelete) {
                    Text(
                        "Hapus",
                        color = Color(0xFFE83D67),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}