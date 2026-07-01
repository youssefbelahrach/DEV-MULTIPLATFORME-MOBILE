package com.example.calculatrice

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.example.calculatrice.ui.SimpleCalculatorScreen

/**
 * PORTRAIT -> Calculatrice SIMPLE.
 *
 * C'est aussi l'activité de lancement (voir AndroidManifest).
 * Verrouillée en portrait ; si l'utilisateur bascule en paysage, la classe
 * de base [OrientationAwareActivity] démarre [LandscapeActivity].
 */
class PortraitActivity : OrientationAwareActivity() {

    override val isPortraitScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // On autorise le capteur pour pouvoir détecter le passage au paysage.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SimpleCalculatorScreen()
                }
            }
        }
    }
}
