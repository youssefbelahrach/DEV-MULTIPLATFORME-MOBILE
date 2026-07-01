import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import '../models/message.dart';

/// Service responsable de la communication avec l'API OpenAI.
class OpenAIService {
  static const String _apiUrl = 'https://api.openai.com/v1/chat/completions';
  static const String _model = 'gpt-4o-mini'; // rapide et économique

  String get _apiKey => dotenv.env['OPENAI_API_KEY'] ?? '';

  /// Envoie l'historique complet de la conversation et retourne
  /// la réponse générée par le modèle.
  Future<String> sendMessage(List<Message> history) async {
    if (_apiKey.isEmpty) {
      throw Exception(
          'Clé API manquante. Ajoutez OPENAI_API_KEY dans le fichier .env');
    }

    final messages = [
      {
        'role': 'system',
        'content':
            'Tu es un assistant utile et amical. Réponds de façon claire et concise.'
      },
      ...history.map((m) => m.toApiJson()),
    ];

    final response = await http
        .post(
          Uri.parse(_apiUrl),
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer $_apiKey',
          },
          body: jsonEncode({
            'model': _model,
            'messages': messages,
            'max_tokens': 500,
            'temperature': 0.7,
          }),
        )
        .timeout(const Duration(seconds: 30));

    if (response.statusCode == 200) {
      final data = jsonDecode(utf8.decode(response.bodyBytes));
      return (data['choices'][0]['message']['content'] as String).trim();
    } else if (response.statusCode == 401) {
      throw Exception('Clé API invalide (erreur 401).');
    } else if (response.statusCode == 429) {
      throw Exception('Quota dépassé ou trop de requêtes (erreur 429).');
    } else {
      throw Exception('Erreur API : ${response.statusCode}');
    }
  }
}
