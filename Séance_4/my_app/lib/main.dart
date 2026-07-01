import 'package:flutter/material.dart';
import 'package:my_app/global/global.parameter.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      routes: GlobalParameters.routes,
      theme: ThemeData(
        primarySwatch: Colors.deepOrange,
        appBarTheme: AppBarTheme(
          backgroundColor: Colors.deepOrange,
          foregroundColor: Colors.white,
        ),
        textTheme: TextTheme(
          // bodyText2 (ancien Flutter) => bodyMedium (Flutter récent)
          bodyMedium: TextStyle(fontSize: 22, color: Colors.deepOrange),
        ),
      ),
    );
  }
}
