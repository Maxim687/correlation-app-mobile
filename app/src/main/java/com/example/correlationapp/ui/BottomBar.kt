package com.example.correlationapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("analyzer") },
            icon = { Icon(Icons.Default.Calculate, contentDescription = "Analyzer") },
            label = { Text("Analyzer") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("files") },
            icon = { Icon(Icons.Default.Folder, contentDescription = "Files") },
            label = { Text("Files") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("about") },
            icon = { Icon(Icons.Default.Info, contentDescription = "About") },
            label = { Text("About") }
        )
    }
}