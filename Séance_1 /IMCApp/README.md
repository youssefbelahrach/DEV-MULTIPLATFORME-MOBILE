# Application IMC (Indice de Masse Corporelle)

Application Android — **II-Master BDCC 1**

Cette application permet de :
- Saisir le **poids** (en Kg) et la **taille** (en Cm) d'une personne
- Calculer et afficher son **Indice de Masse Corporelle (IMC)**
- Afficher l'**image adéquate** correspondant à sa catégorie de masse corporelle

## Formule utilisée

```
IMC = poids (kg) / (taille (m))²
```

La taille est saisie en centimètres puis convertie en mètres.

## Catégories et images

| IMC             | Catégorie         | Image          |
|-----------------|-------------------|----------------|
| < 18,5          | Maigreur          | maigre.png     |
| 18,5 à 24,9     | Normal            | normal.png     |
| 25 à 29,9       | Sur Poids         | surpoids.png   |
| 30 à 34,9       | Obèse             | obese.png      |
| ≥ 35            | Obésité extrême   | t_obese.png    |

> Exemple : Poids = 87 kg, Taille = 177 cm → IMC = **27,77** → **Sur Poids** (image jaune).

## Ouvrir le projet

1. Ouvrir **Android Studio**
2. `File` → `Open...` → sélectionner le dossier **IMCApp**
3. Laisser Gradle se synchroniser (téléchargement automatique des dépendances)
4. Lancer sur un émulateur ou un appareil physique (bouton ▶ Run)

## Configuration technique

- **Langage** : Java
- **compileSdk / targetSdk** : 34
- **minSdk** : 24 (Android 7.0)
- **Android Gradle Plugin** : 8.1.4
- **Gradle** : 8.0
- **JDK requis** : 17

## Structure principale

```
IMCApp/
├── app/
│   └── src/main/
│       ├── java/com/example/imc/MainActivity.java   ← logique de calcul
│       ├── res/layout/activity_main.xml             ← interface
│       ├── res/drawable/                            ← les 5 images + fond du bouton
│       └── res/values/                              ← strings, couleurs, thème
├── build.gradle, settings.gradle                    ← configuration Gradle
└── gradlew, gradlew.bat, gradle/                    ← Gradle Wrapper
```
