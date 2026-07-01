/// Représente un message dans la conversation.
class Message {
  final String content;
  final bool isUser; // true = utilisateur, false = ChatGPT
  final DateTime createdAt;

  Message({
    required this.content,
    required this.isUser,
    DateTime? createdAt,
  }) : createdAt = createdAt ?? DateTime.now();

  /// Format attendu par l'API Chat Completions d'OpenAI.
  Map<String, String> toApiJson() => {
        'role': isUser ? 'user' : 'assistant',
        'content': content,
      };
}
