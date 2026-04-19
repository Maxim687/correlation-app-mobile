package com.example.correlationapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.correlationapp.viewmodel.AnalyzerViewModel
import com.example.correlationapp.ui.ScatterPlot
import androidx.compose.ui.Alignment

@Composable
fun ResultScreen(navController: NavController, viewModel: AnalyzerViewModel) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Result", style = MaterialTheme.typography.headlineLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Coefficient: ${viewModel.result}", style = MaterialTheme.typography.headlineSmall)
        Text("Type: ${viewModel.method}", style = MaterialTheme.typography.headlineSmall)
        Text(viewModel.conclusion, style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        ScatterPlot(
            xValues = viewModel.xValues,
            yValues = viewModel.yValues
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }
    }
}