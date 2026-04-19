package com.example.correlationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.correlationapp.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.correlationapp.viewmodel.AnalyzerViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CorrelationApp()
        }
    }
}

@Composable
fun CorrelationApp() {
    val navController = rememberNavController()

    val viewModel: AnalyzerViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "analyzer",
            modifier = Modifier.padding(padding)
        ) {
            composable("analyzer") { AnalyzerScreen(navController, viewModel) }
            composable("files") { FilesScreen(navController, viewModel) }
            composable("about") { AboutScreen() }
            composable("result") { ResultScreen(navController, viewModel) }
        }
    }
}