package com.example.calculatrice

import android.content.Intent
import android.content.res.Configuration
import androidx.activity.ComponentActivity

/**
 * Activité de base qui redirige vers la bonne activité selon l'orientation.
 *
 * Grâce à `android:configChanges="orientation|screenSize"` dans le manifeste,
 * le système NE recrée PAS l'activité lors d'une rotation : il appelle plutôt
 * [onConfigurationChanged]. On y détecte l'orientation cible et on démarre
 * l'autre activité, puis on ferme l'activité courante.
 */
abstract class OrientationAwareActivity : ComponentActivity() {

    /** Vrai pour PortraitActivity, faux pour LandscapeActivity. */
    abstract val isPortraitScreen: Boolean

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val nowPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT

        // Si l'orientation ne correspond plus à cette activité -> on bascule.
        if (nowPortrait != isPortraitScreen) {
            val target = if (nowPortrait) {
                PortraitActivity::class.java   // -> calculatrice simple
            } else {
                LandscapeActivity::class.java  // -> calculatrice scientifique
            }
            startActivity(
                Intent(this, target).apply {
                    // Évite d'empiler indéfiniment les activités lors des rotations.
                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
            )
            finish()
            overridePendingTransition(0, 0)
        }
    }
}
