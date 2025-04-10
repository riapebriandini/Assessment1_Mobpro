package com.riapebriandini607062330010.asesment1.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.riapebriandini607062330010.asesment1.R
import com.riapebriandini607062330010.asesment1.navigation.Screen
import com.riapebriandini607062330010.asesment1.ui.theme.Asesment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.about_app),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(modifier = Modifier.padding(padding))
    }
}

@SuppressLint("StringFormatMatches")
@Composable
fun ScreenContent(modifier: Modifier) {
    var berat by remember { mutableStateOf("") }
    var tinggi by remember { mutableStateOf("") }
    var usia by remember { mutableStateOf("") }

    var beratError by remember { mutableStateOf(false) }
    var tinggiError by remember { mutableStateOf(false) }
    var usiaError by remember { mutableStateOf(false) }

    var hasilKalori by remember { mutableStateOf<Double?>(null) }

    val genderOptions = listOf(
        stringResource(R.string.male),
        stringResource(R.string.female)
    )
    var gender by remember { mutableStateOf(genderOptions[0]) }

    val aktivitasOptions = listOf(
        "Sangat ringan (tidak olahraga)",
        "Ringan (1–3x/minggu olahraga)",
        "Sedang (3–5x/minggu olahraga)",
        "Berat (6–7x/minggu olahraga)",
        "Sangat berat (2x/hari olahraga)"
    )
    var selectedAktivitas by remember { mutableStateOf(aktivitasOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.salad),
            contentDescription = "Ilustrasi Makanan Sehat",
            modifier = Modifier.padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Makanan Sehat untuk Tubuh Sehat!",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it },
            label = { Text(stringResource(R.string.weight)) },
            trailingIcon = { IconPicker(beratError, "kg") },
            isError = beratError,
            supportingText = { ErrorHint(beratError) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tinggi,
            onValueChange = { tinggi = it },
            label = { Text(stringResource(R.string.height)) },
            trailingIcon = { IconPicker(tinggiError, "cm") },
            isError = tinggiError,
            supportingText = { ErrorHint(tinggiError) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = usia,
            onValueChange = { usia = it },
            label = { Text(stringResource(R.string.age)) },
            trailingIcon = { IconPicker(usiaError, "tahun") },
            isError = usiaError,
            supportingText = { ErrorHint(usiaError) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
        )

        Row(modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(4.dp))) {
            genderOptions.forEach { text ->
                val icon = if (text == stringResource(R.string.male)) Icons.Default.Male else Icons.Default.Female
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick = { gender = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
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
                beratError = berat.isBlank() || berat == "0"
                tinggiError = tinggi.isBlank() || tinggi == "0"
                usiaError = usia.isBlank() || usia == "0"

                if (beratError || tinggiError || usiaError) {
                    hasilKalori = null
                    return@Button
                }

                val b = berat.toDoubleOrNull()
                val t = tinggi.toDoubleOrNull()
                val u = usia.toDoubleOrNull()

                hasilKalori = if (b != null && t != null && u != null) {
                    hitungKalori(b, t, u, gender, selectedAktivitas)
                } else null
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.count))
        }

        hasilKalori?.let { hasil ->
            val iconGender = if (gender == stringResource(R.string.male)) Icons.Filled.Male else Icons.Filled.Female
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(iconGender, contentDescription = null, tint = Color(0xFFEF6C00), modifier = Modifier.padding(end = 12.dp))
                    Text(
                        text = stringResource(R.string.result, hasil),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFEF6C00)
                    )
                }
            }
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
            label = { Text(stringResource(R.string.activity)) },
            enabled = false,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.Gray,
                disabledBorderColor = Color.Gray
            )
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onSelectedChange(label)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null, tint = Color.Red)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError){
        Text(text = stringResource(R.string.input_invalid))
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
        MainScreen(rememberNavController())
    }
}
