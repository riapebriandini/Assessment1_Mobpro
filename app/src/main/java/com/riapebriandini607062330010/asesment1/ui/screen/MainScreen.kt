package com.riapebriandini607062330010.asesment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riapebriandini607062330010.asesment1.R
import com.riapebriandini607062330010.asesment1.ui.theme.Asesment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    var berat by remember { mutableStateOf("") }
    var tinggi by remember { mutableStateOf("") }
    var usia by remember { mutableStateOf("") }
    var hasilKalori by remember { mutableStateOf<String?>(null) }

    val radioOptions = listOf(
        stringResource(id = R.string.pria),
        stringResource(id = R.string.wanita)
    )
    var gender by remember { mutableStateOf(radioOptions[0]) }

    val aktivitasOptions = listOf(
        "Sangat ringan (tidak olahraga)",
        "Ringan (1–3x/minggu olahraga)",
        "Sedang (3–5x/minggu olahraga)",
        "Berat (6–7x/minggu olahraga)",
        "Sangat berat (2x/hari olahraga)"
    )
    var selectedAktivitas by remember { mutableStateOf(aktivitasOptions[0]) }

    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it },
            label = { Text(text = stringResource(R.string.berat)) },
            trailingIcon = { Text("kg") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tinggi,
            onValueChange = { tinggi = it },
            label = { Text(text = stringResource(R.string.tinggi)) },
            trailingIcon = { Text("cm") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = usia,
            onValueChange = { usia = it },
            label = { Text(text = stringResource(R.string.usia)) },
            trailingIcon = { Text("tahun") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(4.dp))) {
            radioOptions.forEach { text ->
                Row(
                    modifier = Modifier
                        .selectable(selected = gender == text, onClick = { gender = text }, role = Role.RadioButton)
                        .weight(1f)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = gender == text, onClick = null)
                    Text(text = text, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        DropdownMenuBox(
            options = aktivitasOptions,
            selected = selectedAktivitas,
            onSelectedChange = { selectedAktivitas = it }
        )

        Button(
            onClick = {
                val b = berat.toDoubleOrNull()
                val t = tinggi.toDoubleOrNull()
                val u = usia.toDoubleOrNull()
                if (b != null && t != null && u != null) {
                    val hasil = hitungKalori(b, t, u, gender, selectedAktivitas)
                    hasilKalori = context.getString(R.string.hasil_kalori, hasil)
                } else {
                    hasilKalori = context.getString(R.string.input_tidak_valid)
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        hasilKalori?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun DropdownMenuBox(options: List<String>, selected: String, onSelectedChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            label = { Text(text = stringResource(R.string.aktivitas)) },
            enabled = false,
            readOnly = true
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { label ->
                DropdownMenuItem(text = { Text(label) }, onClick = {
                    onSelectedChange(label)
                    expanded = false
                })
            }
        }
    }
}

fun hitungKalori(berat: Double, tinggi: Double, usia: Double, gender: String, aktivitas: String): Double {
    val bmr = if (gender == "Pria") {
        (10 * berat) + (6.25 * tinggi) - (5 * usia) + 5
    } else {
        (10 * berat) + (6.25 * tinggi) - (5 * usia) - 161
    }
    val faktorAktivitas = when (aktivitas) {
        "Sangat ringan (tidak olahraga)" -> 1.2
        "Ringan (1–3x/minggu olahraga)" -> 1.375
        "Sedang (3–5x/minggu olahraga)" -> 1.55
        "Berat (6–7x/minggu olahraga)" -> 1.725
        "Sangat berat (2x/hari olahraga)" -> 1.9
        else -> 1.2
    }
    return bmr * faktorAktivitas
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Asesment1Theme {
        MainScreen()
    }
}


