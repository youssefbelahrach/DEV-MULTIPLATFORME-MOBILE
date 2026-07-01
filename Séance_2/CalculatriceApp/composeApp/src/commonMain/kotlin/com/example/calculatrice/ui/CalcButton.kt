package com.example.calculatrice.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Palette de couleurs par catégorie de touche. */
enum class ButtonKind { DIGIT, OPERATOR, FUNCTION, EQUALS }

private fun colorsFor(kind: ButtonKind): Pair<Color, Color> = when (kind) {
    ButtonKind.DIGIT    -> Color(0xFF2E2E33) to Color.White
    ButtonKind.OPERATOR -> Color(0xFFFF9500) to Color.White
    ButtonKind.FUNCTION -> Color(0xFF4A4A50) to Color.White
    ButtonKind.EQUALS   -> Color(0xFF00B894) to Color.White
}

/**
 * Bouton circulaire de calculatrice.
 *
 * @param label      texte affiché sur le bouton
 * @param onClick    GESTION DE L'ÉVÉNEMENT : callback déclenché au clic
 * @param modifier   modifieur de mise en page (poids, taille…)
 * @param kind       catégorie (couleur) du bouton
 */
@Composable
fun CalcButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    kind: ButtonKind = ButtonKind.DIGIT
) {
    val (bg, fg) = colorsFor(kind)
    Button(
        onClick = onClick,               // <-- L'événement onClick est propagé ici
        modifier = modifier.padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = bg, contentColor = fg)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
