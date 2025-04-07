package com.riapebriandini607062330010.asesment1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.riapebriandini607062330010.asesment1.ui.screen.MainScreen
import com.riapebriandini607062330010.asesment1.ui.theme.Asesment1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asesment1Theme {
                MainScreen()
            }
        }
    }


    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
    @Composable
    fun MainScreenPreview() {
        Asesment1Theme {
            MainScreen()
        }
    }
}