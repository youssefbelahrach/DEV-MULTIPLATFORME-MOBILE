// Fichier de build racine.
plugins {
    // Versions déclarées ici, appliquées dans les modules avec `apply false`.
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.multiplatform") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("org.jetbrains.compose") version "1.6.11" apply false
}
