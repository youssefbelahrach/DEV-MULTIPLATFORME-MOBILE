package com.example.calculatrice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.Text
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
import com.example.calculatrice.logic.CalculatorEngine
import com.example.calculatrice.logic.CalculatorState
import com.example.calculatrice.logic.Op
import com.example.calculatrice.logic.UnaryFn
import kotlin.math.E
import kotlin.math.PI

/**
 * CALCULATRICE SCIENTIFIQUE — utilisée en mode PAYSAGE.
 *
 * Ajoute les fonctions sin/cos/tan, ln/log, √, x², xʸ, 1/x, π, e
 * en plus des touches de la calculatrice simple, sur une grille 6 colonnes.
 * La GESTION DES ÉVÉNEMENTS onClick réutilise le même moteur commun.
 */
@Composable
fun ScientificCalculatorScreen() {
    var state by remember { mutableStateOf(CalculatorState()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
            .padding(10.dp)
    ) {
        // ---- Écran d'affichage (plus compact en paysage) ----
        Box(
            modifier = Modifier.fillMaxWidth().weight(0.8f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = state.display,
                color = Color.White,
                fontSize = 48.sp,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        val rowMod = Modifier.fillMaxWidth().weight(1f)
        val cellMod = Modifier.weight(1f).fillMaxHeight()

        // Ligne 1 : fonctions scientifiques + édition
        Row(rowMod) {
            CalcButton("sin", { state = CalculatorEngine.applyUnary(state, UnaryFn.SIN) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("cos", { state = CalculatorEngine.applyUnary(state, UnaryFn.COS) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("tan", { state = CalculatorEngine.applyUnary(state, UnaryFn.TAN) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("C", { state = CalculatorEngine.clear() }, cellMod, ButtonKind.FUNCTION)
            CalcButton("⌫", { state = CalculatorEngine.backspace(state) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("÷", { state = CalculatorEngine.setOperator(state, Op.DIV) }, cellMod, ButtonKind.OPERATOR)
        }
        // Ligne 2 : ln/log/√ + 7 8 9 ×
        Row(rowMod) {
            CalcButton("ln", { state = CalculatorEngine.applyUnary(state, UnaryFn.LN) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("log", { state = CalculatorEngine.applyUnary(state, UnaryFn.LOG) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("√", { state = CalculatorEngine.applyUnary(state, UnaryFn.SQRT) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("7", { state = CalculatorEngine.inputDigit(state, 7) }, cellMod)
            CalcButton("8", { state = CalculatorEngine.inputDigit(state, 8) }, cellMod)
            CalcButton("9", { state = CalculatorEngine.inputDigit(state, 9) }, cellMod)
        }
        // Ligne 3 : x²/xʸ/1/x + 4 5 6 −
        Row(rowMod) {
            CalcButton("x²", { state = CalculatorEngine.applyUnary(state, UnaryFn.SQUARE) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("xʸ", { state = CalculatorEngine.setOperator(state, Op.POW) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("1/x", { state = CalculatorEngine.applyUnary(state, UnaryFn.INVERSE) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("4", { state = CalculatorEngine.inputDigit(state, 4) }, cellMod)
            CalcButton("5", { state = CalculatorEngine.inputDigit(state, 5) }, cellMod)
            CalcButton("×", { state = CalculatorEngine.setOperator(state, Op.MUL) }, cellMod, ButtonKind.OPERATOR)
        }
        // Ligne 4 : π e % + 1 2 3
        Row(rowMod) {
            CalcButton("π", { state = CalculatorEngine.inputConstant(state, PI) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("e", { state = CalculatorEngine.inputConstant(state, E) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("%", { state = CalculatorEngine.applyUnary(state, UnaryFn.PERCENT) }, cellMod, ButtonKind.FUNCTION)
            CalcButton("1", { state = CalculatorEngine.inputDigit(state, 1) }, cellMod)
            CalcButton("2", { state = CalculatorEngine.inputDigit(state, 2) }, cellMod)
            CalcButton("3", { state = CalculatorEngine.inputDigit(state, 3) }, cellMod)
        }
        // Ligne 5 : ± 6 . 0 − +  puis =
        Row(rowMod) {
            CalcButton("±", { state = CalculatorEngine.applyUnary(state, UnaryFn.NEGATE) }, cellMod)
            CalcButton("6", { state = CalculatorEngine.inputDigit(state, 6) }, cellMod)
            CalcButton("0", { state = CalculatorEngine.inputDigit(state, 0) }, cellMod)
            CalcButton(".", { state = CalculatorEngine.inputDot(state) }, cellMod)
            CalcButton("−", { state = CalculatorEngine.setOperator(state, Op.SUB) }, cellMod, ButtonKind.OPERATOR)
            CalcButton("+", { state = CalculatorEngine.setOperator(state, Op.ADD) }, cellMod, ButtonKind.OPERATOR)
        }
        // Ligne 6 : = (pleine largeur)
        Row(rowMod) {
            CalcButton("=", { state = CalculatorEngine.evaluate(state) }, cellMod, ButtonKind.EQUALS)
        }
    }
}
