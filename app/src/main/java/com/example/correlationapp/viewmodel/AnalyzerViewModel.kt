package com.example.correlationapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlin.math.*

class AnalyzerViewModel : ViewModel() {

    var result by mutableStateOf("")
    var method by mutableStateOf("")
    var conclusion by mutableStateOf("")

    var xInput by mutableStateOf("")

    var yInput by mutableStateOf("")

    var selectedMethod by mutableStateOf("Pearson")

    var errorMessage by mutableStateOf<String?>(null)

    var xValues by mutableStateOf<List<Double>>(emptyList())

    var yValues by mutableStateOf<List<Double>>(emptyList())

    fun calculate(xInput: String, yInput: String, selectedMethod: String) {

        xError = null
        yError = null

        val x = parseInputSafe(xInput)
        val y = parseInputSafe(yInput)

        if (x == null) {
            xError = "Invalid X values"
            return
        }

        if (y == null) {
            yError = "Invalid Y values"
            return
        }

        if (x.size != y.size) {
            errorMessage = "X and Y must have same length"
            return
        }

        val coeff = when (selectedMethod) {
            "Pearson" -> calculatePearson(x, y)
            "Spearman" -> calculateSpearman(x, y)
            "Kendall" -> calculateKendall(x, y)
            else -> 0.0
        }

        result = String.format("%.4f", coeff)
        method = selectedMethod
        conclusion = getConclusion(selectedMethod, coeff)

        xValues = x
        yValues = y
    }

    private fun parseInputSafe(input: String): List<Double>? {
        return try {
            input.split(",", " ")
                .filter { it.isNotBlank() }
                .map { it.toDouble() }
        } catch (e: Exception) {
            null
        }
    }

    fun clear() {
        xInput = ""
        yInput = ""
        selectedMethod = "Pearson"
        result = ""
        method = ""
        conclusion = ""
        errorMessage = null
    }

    private fun parseInput(input: String): List<Double> {
        return input.split(",", " ")
            .filter { it.isNotBlank() }
            .map { it.toDouble() }
    }

    private fun calculatePearson(x: List<Double>, y: List<Double>): Double {
        val xMean = x.average()
        val yMean = y.average()

        var numerator = 0.0
        var sumX = 0.0
        var sumY = 0.0

        for (i in x.indices) {
            val dx = x[i] - xMean
            val dy = y[i] - yMean
            numerator += dx * dy
            sumX += dx * dx
            sumY += dy * dy
        }

        return if (sumX == 0.0 || sumY == 0.0) 0.0
        else numerator / sqrt(sumX * sumY)
    }

    private fun calculateSpearman(x: List<Double>, y: List<Double>): Double {
        val xRanks = rank(x)
        val yRanks = rank(y)
        return calculatePearson(xRanks, yRanks)
    }

    private fun calculateKendall(x: List<Double>, y: List<Double>): Double {
        var concordant = 0
        var discordant = 0

        for (i in x.indices) {
            for (j in i + 1 until x.size) {
                val sign = (x[i] - x[j]) * (y[i] - y[j])
                if (sign > 0) concordant++
                else if (sign < 0) discordant++
            }
        }

        val total = x.size * (x.size - 1) / 2.0
        return if (total == 0.0) 0.0 else (concordant - discordant) / total
    }

    private fun rank(values: List<Double>): List<Double> {
        return values.sorted().map { values.indexOf(it) + 1.0 }
    }

    private fun getConclusion(type: String, value: Double): String {
        val abs = kotlin.math.abs(value)

        return when {
            abs >= 0.7 -> "Strong ${if (value > 0) "positive" else "negative"} correlation"
            abs >= 0.3 -> "Moderate ${if (value > 0) "positive" else "negative"} correlation"
            else -> "Weak or no correlation"
        }
    }

    fun isValid(): Boolean {
        return xInput.isNotBlank() && yInput.isNotBlank()
    }

    var xError by mutableStateOf<String?>(null)
    var yError by mutableStateOf<String?>(null)
}