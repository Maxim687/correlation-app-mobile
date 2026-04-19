package com.example.correlationapp.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.correlationapp.viewmodel.AnalyzerViewModel
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun FilesScreen(
    navController: NavController,
    viewModel: AnalyzerViewModel
) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        selectedFileUri = uri

        uri?.let {
            val result = readFile(context, it)

            if (result != null) {
                viewModel.xInput = result.first
                viewModel.yInput = result.second

                // 🔥 автоматично переходимо на головний екран
                navController.navigate("analyzer")
            } else {
                viewModel.errorMessage = "Failed to read file"
            }
        }
    }

    // 🔥 показ помилки
    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.errorMessage = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                "Import Data",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    filePicker.launch(arrayOf("*/*"))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Choose TXT / CSV File")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = selectedFileUri?.toString() ?: "No file selected",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun readFile(context: android.content.Context, uri: Uri): Pair<String, String>? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))

        val xList = mutableListOf<Double>()
        val yList = mutableListOf<Double>()

        reader.forEachLine { line ->
            val parts = line.split(",", " ", ";")
                .filter { it.isNotBlank() }

            if (parts.size >= 2) {
                xList.add(parts[0].toDouble())
                yList.add(parts[1].toDouble())
            }
        }

        Pair(
            xList.joinToString(" "),
            yList.joinToString(" ")
        )

    } catch (e: Exception) {
        null
    }
}