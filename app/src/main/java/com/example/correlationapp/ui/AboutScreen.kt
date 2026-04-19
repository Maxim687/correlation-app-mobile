package com.example.correlationapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun AboutScreen() {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Text(
                text = "Correlation Analyzer",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Version 1.0",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "This application allows you to calculate correlation between two datasets using different statistical methods.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Supported methods:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("• Pearson — linear correlation", style = MaterialTheme.typography.bodyLarge)
            Text("• Spearman — rank correlation", style = MaterialTheme.typography.bodyLarge)
            Text("• Kendall — ordinal association", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Features:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("• Manual data input", style = MaterialTheme.typography.bodyLarge)
            Text("• File import (TXT, CSV)", style = MaterialTheme.typography.bodyLarge)
            Text("• Scatter plot visualization", style = MaterialTheme.typography.bodyLarge)
        }

    }
}