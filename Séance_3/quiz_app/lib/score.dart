import 'package:flutter/material.dart';

class Score extends StatelessWidget {
  final int _score;
  final Function() _resetQuiz;
  final int _numberOfQuestions;

  const Score(this._score, this._resetQuiz, this._numberOfQuestions,
      {super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20),
      child: Column(
        children: <Widget>[
          Text(
            'Results: Your score is ${_score / _numberOfQuestions * 100} %',
            style: const TextStyle(fontSize: 20),
          ),
          TextButton(
            onPressed: _resetQuiz,
            child: const Text(
              'Restart',
              style: TextStyle(fontSize: 20, color: Colors.blue),
            ),
          ),
        ],
      ),
    );
  }
}
