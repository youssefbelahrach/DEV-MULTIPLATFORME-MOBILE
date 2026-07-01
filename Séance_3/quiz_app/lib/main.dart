import 'package:flutter/material.dart';

import 'quiz.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          title: const Text('Quiz App'),
          backgroundColor: Colors.orangeAccent,
        ),
        body: const Center(
          child: Quiz(),
        ),
      ),
    );
  }
}
