package br.com.schuster.androidcleanarchitecture.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.schuster.androidcleanarchitecture.presentation.navigation.AppNavHost
import br.com.schuster.androidcleanarchitecture.presentation.ui.theme.AndroidCleanArchitectureTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AndroidCleanArchitectureTheme {
                AppNavHost()
            }
        }
    }
}

