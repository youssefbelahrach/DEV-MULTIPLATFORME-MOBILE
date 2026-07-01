package com.example.calculatrice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.example.calculatrice.logic.CalculatorEngine
import com.example.calculatrice.logic.CalculatorState
import com.example.calculatrice.logic.Op
import com.example.calculatrice.logic.UnaryFn

/**
 * CALCULATRICE SIMPLE — utilisée en mode PORTRAIT.
 *
 * Grille 4 colonnes : chiffres, 4 opérateurs de base, C, ⌫, %, = .
 * Toute la GESTION DES ÉVÉNEMENTS onClick met à jour l'état via le moteur commun.
 */
@Composable
fun SimpleCalculatorScreen() {
    var state by remember { mutableStateOf(CalculatorState()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
            .padding(12.dp)
    ) {
        // ---- Écran d'affichage ----
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = state.display,
                color = Color.White,
                fontSize = 64.sp,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        // ---- Clavier ----
        val rowMod = Modifier.fillMaxWidth().weight(1f)
        val cellMod = Modifier.weight(1f).fillMaxSize()

        Row(rowMod, horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            CalcButton("C", { state = CalculatorEngine.clear() }, cellMod, ButtonKind.FUNCTION)
            CalcButton("⌫", { state = CalculatorEngine.backspace(state) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("%", { state = CalculatorEngine.applyUnary(state, UnaryFn.PERCENT) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("÷", { state = CalculatorEngine.setOperator(state, Op.DIV) }, cellMod, ButtonKind.OPERATOR)
        }
        Row(rowMod) {
            CalcButton("7", { state = CalculatorEngine.inputDigit(state, 7) }, cellMod)
            CalcButton("8", { state = CalculatorEngine.inputDigit(state, 8) }, cellMod)
            CalcButton("9", { state = CalculatorEngine.inputDigit(state, 9) }, cellMod)
            CalcButton("×", { state = CalculatorEngine.setOperator(state, Op.MUL) }, cellMod, ButtonKind.OPERATOR)
        }
        Row(rowMod) {
            CalcButton("4", { state = CalculatorEngine.inputDigit(state, 4) }, cellMod)
            CalcButton("5", { state = CalculatorEngine.inputDigit(state, 5) }, cellMod)
            CalcButton("6", { state = CalculatorEngine.inputDigit(state, 6) }, cellMod)
            CalcButton("−", { state = CalculatorEngine.setOperator(state, Op.SUB) }, cellMod, ButtonKind.OPERATOR)
        }
        Row(rowMod) {
            CalcButton("1", { state = CalculatorEngine.inputDigit(state, 1) }, cellMod)
            CalcButton("2", { state = CalculatorEngine.inputDigit(state, 2) }, cellMod)
            CalcButton("3", { state = CalculatorEngine.inputDigit(state, 3) }, cellMod)
            CalcButton("+", { state = CalculatorEngine.setOperator(state, Op.ADD) }, cellMod, ButtonKind.OPERATOR)
        }
        Row(rowMod) {
            CalcButton("±", { state = CalculatorEngine.applyUnary(state, UnaryFn.NEGATE) }, cellMod)
            CalcButton("0", { state = CalculatorEngine.inputDigit(state, 0) }, cellMod)
            CalcButton(".", { state = CalculatorEngine.inputDot(state) }, cellMod)
            CalcButton("=", { state = CalculatorEngine.evaluate(state) }, cellMod, ButtonKind.EQUALS)
        }
    }
}
