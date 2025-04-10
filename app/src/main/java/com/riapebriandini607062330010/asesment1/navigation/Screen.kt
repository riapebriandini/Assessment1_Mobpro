package com.riapebriandini607062330010.asesment1.navigation

import android.media.MediaRouter

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}