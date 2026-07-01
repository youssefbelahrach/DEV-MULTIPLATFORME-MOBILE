package com.example.calculatrice.logic

import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * Moteur de calcul partagé (commonMain) — indépendant de la plateforme.
 *
 * Il conserve l'état d'affichage sous forme immuable ([CalculatorState]) et
 * expose des fonctions pures qui prennent l'état courant + une action et
 * renvoient le nouvel état. Ce découpage rend la logique testable et
 * réutilisable aussi bien par la calculatrice simple que scientifique.
 */

/** État immuable de la calculatrice. */
data class CalculatorState(
    val display: String = "0",          // Ce qui est affiché à l'écran
    val accumulator: Double? = null,     // Opérande gauche mémorisée
    val pendingOp: Op? = null,           // Opérateur en attente (+, -, ×, ÷)
    val resetOnNextDigit: Boolean = false // Faut-il repartir de zéro à la prochaine saisie ?
)

/** Opérateurs binaires supportés. */
enum class Op(val symbol: String) {
    ADD("+"), SUB("−"), MUL("×"), DIV("÷"), POW("xʸ")
}

object CalculatorEngine {

    // ----------------------- Saisie des chiffres -----------------------

    /** Ajoute un chiffre (0..9) à l'affichage. */
    fun inputDigit(state: CalculatorState, digit: Int): CalculatorState {
        val base = if (state.resetOnNextDigit || state.display == "0") "" else state.display
        return state.copy(display = (base + digit), resetOnNextDigit = false)
    }

    /** Ajoute la virgule décimale (une seule autorisée). */
    fun inputDot(state: CalculatorState): CalculatorState {
        if (state.resetOnNextDigit) return state.copy(display = "0.", resetOnNextDigit = false)
        if (state.display.contains(".")) return state
        return state.copy(display = state.display + ".")
    }

    // ----------------------- Opérateurs binaires -----------------------

    /** Enregistre un opérateur binaire, en calculant l'opération précédente si besoin. */
    fun setOperator(state: CalculatorState, op: Op): CalculatorState {
        val current = state.display.toDoubleOrNull() ?: 0.0
        // S'il y a déjà une opération en attente, on la résout d'abord (chaînage).
        val newAccumulator = if (state.accumulator != null && state.pendingOp != null && !state.resetOnNextDigit) {
            apply(state.accumulator, current, state.pendingOp)
        } else {
            current
        }
        return state.copy(
            display = format(newAccumulator),
            accumulator = newAccumulator,
            pendingOp = op,
            resetOnNextDigit = true
        )
    }

    /** Résout l'opération en attente (touche =). */
    fun evaluate(state: CalculatorState): CalculatorState {
        val op = state.pendingOp ?: return state
        val left = state.accumulator ?: return state
        val right = state.display.toDoubleOrNull() ?: 0.0
        val result = apply(left, right, op)
        return CalculatorState(display = format(result), resetOnNextDigit = true)
    }

    private fun apply(a: Double, b: Double, op: Op): Double = when (op) {
        Op.ADD -> a + b
        Op.SUB -> a - b
        Op.MUL -> a * b
        Op.DIV -> if (b == 0.0) Double.NaN else a / b
        Op.POW -> a.pow(b)
    }

    // ----------------------- Fonctions unaires (scientifiques) -----------------------

    fun applyUnary(state: CalculatorState, fn: UnaryFn): CalculatorState {
        val x = state.display.toDoubleOrNull() ?: 0.0
        val result = when (fn) {
            UnaryFn.SIN -> sin(x)
            UnaryFn.COS -> cos(x)
            UnaryFn.TAN -> tan(x)
            UnaryFn.SQRT -> if (x < 0) Double.NaN else sqrt(x)
            UnaryFn.LN -> if (x <= 0) Double.NaN else ln(x)
            UnaryFn.LOG -> if (x <= 0) Double.NaN else log10(x)
            UnaryFn.SQUARE -> x * x
            UnaryFn.INVERSE -> if (x == 0.0) Double.NaN else 1.0 / x
            UnaryFn.NEGATE -> -x
            UnaryFn.PERCENT -> x / 100.0
        }
        return state.copy(display = format(result), resetOnNextDigit = true)
    }

    /** Insère une constante (π ou e). */
    fun inputConstant(state: CalculatorState, value: Double): CalculatorState =
        state.copy(display = format(value), resetOnNextDigit = true)

    // ----------------------- Édition -----------------------

    /** Efface tout (touche C / AC). */
    fun clear(): CalculatorState = CalculatorState()

    /** Efface le dernier caractère (touche ⌫). */
    fun backspace(state: CalculatorState): CalculatorState {
        if (state.resetOnNextDigit) return state
        val newDisplay = state.display.dropLast(1)
        return state.copy(display = if (newDisplay.isEmpty() || newDisplay == "-") "0" else newDisplay)
    }

    // ----------------------- Utilitaires -----------------------

    /** Formatte un Double : entier propre si possible, sinon décimal, gestion NaN. */
    private fun format(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "Erreur"
        return if (value % 1.0 == 0.0 && kotlin.math.abs(value) < 1e15) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}

enum class UnaryFn {
    SIN, COS, TAN, SQRT, LN, LOG, SQUARE, INVERSE, NEGATE, PERCENT
}
