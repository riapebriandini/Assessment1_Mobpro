package com.riapebriandini607062330010.asesment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.riapebriandini607062330010.asesment1.navigation.SetupNavGraph
import com.riapebriandini607062330010.asesment1.ui.theme.Asesment1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asesment1Theme {
                SetupNavGraph()
            }
        }
    }
}

//    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//    @Composable
//    fun MainScreenPreview() {
//        Asesment1Theme {
//            MainScreen()
//        }
//    }
//}