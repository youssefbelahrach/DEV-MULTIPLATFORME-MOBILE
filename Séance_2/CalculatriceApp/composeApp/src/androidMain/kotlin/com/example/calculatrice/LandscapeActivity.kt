package com.example.calculatrice

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.calculatrice.ui.ScientificCalculatorScreen

/**
 * PAYSAGE -> Calculatrice SCIENTIFIQUE.
 *
 * Démarrée automatiquement lorsque l'appareil passe en paysage depuis
 * [PortraitActivity]. Si l'utilisateur revient en portrait, la classe de base
 * [OrientationAwareActivity] relance [PortraitActivity].
 */
class LandscapeActivity : OrientationAwareActivity() {

    override val isPortraitScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ScientificCalculatorScreen()
                }
            }
        }
    }
}
