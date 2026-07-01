import 'package:flutter/material.dart';

class Question extends StatelessWidget {
  final String _question;
  final int _currentQuestionIndex;
  final int _numberOfQuestions;

  const Question(
    this._question,
    this._currentQuestionIndex,
    this._numberOfQuestions, {
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Text(
          '${_currentQuestionIndex + 1}/$_numberOfQuestions',
          style: const TextStyle(fontSize: 22),
        ),
        Container(
          width: double.infinity,
          padding: const EdgeInsets.all(10),
          margin: const EdgeInsets.all(10),
          child: Text(
            _question,
            style: const TextStyle(fontSize: 20),
            textAlign: TextAlign.center,
          ),
        ),
      ],
    );
  }
}
