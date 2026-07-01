# ChatBot GPT

Application ChatBot qui interagit avec l'API d'OpenAI (Chat Completions).

## Fonctionnalités

- **Splash screen animé**
- **Authentification**
- **Navigation**
- **Chat**
- **Design**

## Structure du projet

```
lib/
├── main.dart                  # Point d'entrée + routes
├── theme/app_theme.dart
├── models/message.dart        # Modèle de message
├── services/
│   ├── openai_service.dart    # Appels HTTP à l'API OpenAI
│   └── auth_service.dart      # Authentification locale
├── screens/
│   ├── splash_screen.dart
│   ├── login_screen.dart
│   └── chat_screen.dart
└── widgets/
    ├── message_bubble.dart
    └── typing_indicator.dart
```

## Installation

1. Créer un projet Flutter puis remplacer les fichiers :

   ```bash
   flutter create chatbot_gpt
   ```

   Copier `lib/`, `pubspec.yaml`, `.env.example` dans le projet.

2. Installer les dépendances :

   ```bash
   flutter pub get
   ```

3. Créer le fichier `.env` à la racine du projet (à côté de `pubspec.yaml`) :

   ```
   OPENAI_API_KEY=sk-votre-cle
   ```

4. Lancer l'application :
   ```bash
   flutter run
   ```
