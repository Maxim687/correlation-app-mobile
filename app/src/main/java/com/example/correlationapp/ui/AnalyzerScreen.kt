package com.example.correlationapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.correlationapp.viewmodel.AnalyzerViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AnalyzerScreen(navController: NavController, viewModel: AnalyzerViewModel) {

    val snackbarHostState = remember { SnackbarHostState() }

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
                text = "Correlation Analyzer",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = viewModel.xInput,
                onValueChange = { viewModel.xInput = it },
                label = { Text("X values") },
                placeholder = { Text("for example: 5, 6, 7, 8") },
                isError = viewModel.xError != null,
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.xError != null) {
                Text(viewModel.xError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.yInput,
                onValueChange = { viewModel.yInput = it },
                label = { Text("Y values") },
                placeholder = { Text("for example: 5, 6, 7, 8") },
                isError = viewModel.yError != null,
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.yError != null) {
                Text(viewModel.yError!!, color = MaterialTheme.colorScheme.error)
            }

            if (viewModel.xError != null) {
                Text(viewModel.xError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Method:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                RadioGroupOption("Pearson", viewModel.selectedMethod == "Pearson") {
                    viewModel.selectedMethod = "Pearson"
                }
                RadioGroupOption("Spearman", viewModel.selectedMethod == "Spearman") {
                    viewModel.selectedMethod = "Spearman"
                }
                RadioGroupOption("Kendall", viewModel.selectedMethod == "Kendall") {
                    viewModel.selectedMethod = "Kendall"
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.calculate(
                        viewModel.xInput,
                        viewModel.yInput,
                        viewModel.selectedMethod
                    )

                    if (viewModel.errorMessage == null &&
                        viewModel.xError == null &&
                        viewModel.yError == null
                    ) {
                        navController.navigate("result")
                    }
                },
                enabled = viewModel.isValid(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.clear()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear")
            }
        }
    }
}

@Composable
private fun RadioGroupOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}