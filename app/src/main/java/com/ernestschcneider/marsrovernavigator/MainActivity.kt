package com.ernestschcneider.marsrovernavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           MarsRoverNavigatorTheme {
                MainApp()
            }
        }
    }
}
