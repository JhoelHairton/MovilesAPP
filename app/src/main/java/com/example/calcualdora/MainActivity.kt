package com.example.calcualdora


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.sqrt



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraApp()
        }
    }
}

@Composable
fun CalculadoraApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3E5FC))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = input,
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.End,
            color = Color(0xFF6200EA)
        )
        Text(
            text = result,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.End,
            color = Color(0xFF6200EA)
        )
        Spacer(modifier = Modifier.height(10.dp))
        CalculatorButtons(
            input = input,
            onValueChange = { newValue ->
                input = newValue
            },
            onCalculate = { operation ->
                result = performOperation(input, operation)
                if (operation == "=") {
                    input = result
                } else {
                    input = ""
                }
            }
        )
    }
}

@Composable
fun CalculatorButtons(input: String, onValueChange: (String) -> Unit, onCalculate: (String) -> Unit) {
    val buttonColor = Color(0xFF00BCD4)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("AC", buttonColor, Modifier.weight(1f)) { onValueChange("") }
            CalculatorButton("√", buttonColor, Modifier.weight(1f)) { onCalculate("√") }
            CalculatorButton("x^2", buttonColor, Modifier.weight(1f)) { onCalculate("x^2") }
            CalculatorButton("/", buttonColor, Modifier.weight(1f)) { onValueChange(input + "/") }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("7", buttonColor, Modifier.weight(1f)) { onValueChange(input + "7") }
            CalculatorButton("8", buttonColor, Modifier.weight(1f)) { onValueChange(input + "8") }
            CalculatorButton("9", buttonColor, Modifier.weight(1f)) { onValueChange(input + "9") }
            CalculatorButton("*", buttonColor, Modifier.weight(1f)) { onValueChange(input + "*") }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("4", buttonColor, Modifier.weight(1f)) { onValueChange(input + "4") }
            CalculatorButton("5", buttonColor, Modifier.weight(1f)) { onValueChange(input + "5") }
            CalculatorButton("6", buttonColor, Modifier.weight(1f)) { onValueChange(input + "6") }
            CalculatorButton("-", buttonColor, Modifier.weight(1f)) { onValueChange(input + "-") }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("1", buttonColor, Modifier.weight(1f)) { onValueChange(input + "1") }
            CalculatorButton("2", buttonColor, Modifier.weight(1f)) { onValueChange(input + "2") }
            CalculatorButton("3", buttonColor, Modifier.weight(1f)) { onValueChange(input + "3") }
            CalculatorButton("+", buttonColor, Modifier.weight(1f)) { onValueChange(input + "+") }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("0", buttonColor, Modifier.weight(1f)) { onValueChange(input + "0") }
            CalculatorButton("π", buttonColor, Modifier.weight(1f)) { onCalculate("π") }
            CalculatorButton("1/x", buttonColor, Modifier.weight(1f)) { onCalculate("1/x") }
            CalculatorButton("=", buttonColor, Modifier.weight(1f)) { onCalculate("=") }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        }
    }
}

@Composable
fun CalculatorButton(label: String, backgroundColor: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Asegura que los botones sean cuadrados
            .padding(4.dp),  // Espaciado entre los botones
        colors = ButtonDefaults.buttonColors(backgroundColor)
    ) {
        Text(text = label, fontSize = 24.sp, color = Color(0xFF6200EA))
    }
}

fun performOperation(input: String, operation: String): String {
    return try {
        when (operation) {
            "%" -> input.toDoubleOrNull()?.let { (it / 100).toString() } ?: "Error"
            "π" -> Math.PI.toString()
            "√" -> input.toDoubleOrNull()?.let { sqrt(it).toString() } ?: "Error"
            "x^2" -> input.toDoubleOrNull()?.let { it.pow(2).toString() } ?: "Error"
            "1/x" -> input.toDoubleOrNull()?.let { (1 / it).toString() } ?: "Error"
            "=" -> evaluateExpression(input)
            else -> input + operation
        }
    } catch (e: Exception) {
        "Error"
    }
}

fun calculateSimpleExpression(tokens: List<String>): Double {
    if (tokens.size < 3) return tokens[0].toDoubleOrNull() ?: Double.NaN

    var result = tokens[0].toDouble()
    var index = 1
    while (index < tokens.size) {
        val operator = tokens[index]
        val nextValue = tokens[index + 1].toDoubleOrNull() ?: return Double.NaN
        result = when (operator) {
            "+" -> result + nextValue
            "-" -> result - nextValue
            "*" -> result * nextValue
            "/" -> result / nextValue
            else -> return Double.NaN
        }
        index += 2
    }
    return result
}

fun evaluateExpression(expression: String): String {
    return try {
        val tokens = tokenize(expression)
        val result = calculateSimpleExpression(tokens)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

fun tokenize(expression: String): List<String> {
    val regex = Regex("([*/+-])")
    val parts = expression.split(regex)
    val operators = regex.findAll(expression).map { it.value }.toList()

    return parts.zip(operators + "").flatMap { listOf(it.first, it.second) }.filter { it.isNotEmpty() }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraApp()
}