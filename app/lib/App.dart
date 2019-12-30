import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'screens/AccountScreen.dart';
import 'screens/StartScreen.dart';

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
      home: AccountScreen(),
    );
  }
}

String baseUrl = kIsWeb ? "http://localhost:8080" : "http://10.0.2.2:8080";
