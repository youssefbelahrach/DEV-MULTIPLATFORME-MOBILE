import 'package:flutter/material.dart';

class Answer extends StatelessWidget {
  final Map<String, Object> _answer;
  final void Function(Map<String, Object>) _handleAnswer;

  const Answer(this._answer, this._handleAnswer, {super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(10),
      width: double.infinity,
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.orange,
          foregroundColor: Colors.white,
          padding: const EdgeInsets.all(10),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        onPressed: () => _handleAnswer(_answer),
        child: Align(
          alignment: Alignment.centerLeft,
          child: Text(
            _answer['answer'] as String,
            style: const TextStyle(fontSize: 16),
            textAlign: TextAlign.left,
          ),
        ),
      ),
    );
  }
}
