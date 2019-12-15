import 'package:flutter/material.dart';

import 'StartScreen.dart';

class App extends StatefulWidget {
  @override
  _AppState createState() => _AppState();
}

class _AppState extends State<App> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SpookyScarySkeletons',
      theme: ThemeData(
        primaryColor: Colors.red,
        primarySwatch: Colors.red,
        primaryColorDark: Colors.red[800],
      ),
      home: StartScreen(),
    );
  }
}
