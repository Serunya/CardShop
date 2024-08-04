package com.tailspin.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.tailspin.presentation.product.ProductScreen
import com.tailspin.presentation.theme.ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                SetSystemThem()
                Scaffold {paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                        ProductScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun SetSystemThem() {
        val color = MaterialTheme.colorScheme.primary
        enableEdgeToEdge(
            SystemBarStyle.light(color.toArgb(),color.toArgb()),
            SystemBarStyle.light(color.toArgb(),color.toArgb())
        )
    }

}
