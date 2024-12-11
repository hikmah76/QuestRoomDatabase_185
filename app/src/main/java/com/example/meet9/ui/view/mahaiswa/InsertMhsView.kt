package com.example.meet9.ui.view.mahaiswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet9.ui.navigation.AlamatNavigasi
import com.example.meet9.ui.viewmodel.MahasiswaEvent
import com.example.meet9.ui.viewmodel.MahasiswaViewModel
import com.example.meet9.ui.viewmodel.MhsUIState
import com.example.meet9.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


object DestinasiInsert : AlamatNavigasi { //hlm insertmhs dikenal dgn destinasiinsert
    override val route: String = "insert_mhs"
}

@Composable
fun InsertMhsView(
    onBack: () -> Unit, //untuk menavigasi di penglola hlm
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(factory = PenyediaViewModel.factory) // Inisialiasi viewmodel
) {
    val uiState = viewModel.uiState // Ambil UI state dari viewmodel
    val snackbarHostState = remember {SnackbarHostState()} // Snackbar state
    val caroutineScope = rememberCoroutineScope() // Snackbarnya agar bisa muncul ektika button diklik

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { messsage ->
            caroutineScope.launch {
                snackbarHostState.showSnackbar(messsage) // Tampilkan Snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold  (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } //Tempatkan snackbar di dalam Scaffold
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ){
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa"
            )
            // Isi Body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateState(updateEvent) // Update state di viewmodel
                },
                onClick = {
                    caroutineScope.launch {
                        viewModel.saveData() //Simpan data
                    }
                    onNavigate("")
                }
            )
        }
    }
}
// Fungsi utama untuk menampilkan formulir dan tombol simpan
@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid, // Menggunakan formErrorState yang sesuai
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

// Fungsi untuk menampilkan formulir mahasiswa. yang pertama dibuat. lalu dipanggil di bodyInsert
@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Nama
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it)) },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        // NIM
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nim = it)) },
            label = { Text("NIM") },
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )

        // Jenis Kelamin
        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            jenisKelamin.forEach { jk ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = { onValueChange(mahasiswaEvent.copy(jenisKelamin = jk)) }
                    )
                    Text(text = jk)
                }
            }
        }
        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )

        // Alamat
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = { onValueChange(mahasiswaEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan alamat") }
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        // Kelas
        Text(text = "Kelas")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            kelas.forEach { kls ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kls,
                        onClick = { onValueChange(mahasiswaEvent.copy(kelas = kls)) }
                    )
                    Text(text = kls)
                }
            }
        }
        Text(
            text = errorState.kelas ?: "",
            color = Color.Red
        )

        // Angkatan
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = { onValueChange(mahasiswaEvent.copy(angkatan = it)) },
            label = { Text("Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}

// Kelas data untuk MahasiswaEvent
data class MahasiswaEvent(
    val nama: String = "",
    val nim: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

// Kelas data untuk FormErrorState
data class FormErrorState(
    val nama: String? = null,
    val nim: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null
)

// Kelas data untuk UI State
data class MhsUiState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val formErrorState: FormErrorState = FormErrorState()
)