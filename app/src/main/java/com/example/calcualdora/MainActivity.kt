package com.example.calcualdora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var numberInput by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = numberInput,
            onValueChange = { numberInput = it },
            label = { Text("Ingrese un número") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val number = numberInput.text.toDoubleOrNull()
                result = if (number != null) {
                    "Resultado: ${number.pow(2)}"
                } else {
                    "Por favor, ingrese un número válido"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Potencia")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val number = numberInput.text.toDoubleOrNull()
                result = if (number != null) {
                    "Resultado: ${sqrt(number)}"
                } else {
                    "Por favor, ingrese un número válido"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sacar Raíz Cuadrada")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val number = numberInput.text.toDoubleOrNull()
                result = if (number != null) {
                    if (number != 0.0) {
                        "Resultado: ${1 / number}"
                    } else {
                        "No se puede dividir por cero"
                    }
                } else {
                    "Por favor, ingrese un número válido"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular 1/Número")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                result = "Resultado: ${Math.PI}"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mostrar Pi")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = result, style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraApp()}