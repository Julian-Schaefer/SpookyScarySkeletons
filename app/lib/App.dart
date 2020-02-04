import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:io' show Platform;
import '.env.dart';

import 'screens/AccountScreen.dart';

class App extends StatefulWidget {
  @override
  _AppState createState() => _AppState();
}

class _AppState extends State<App> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Spooky Scary Chat Game',
      theme: ThemeData(
        brightness: Brightness.dark,
        canvasColor: Color(0xff121212),
        primaryColor: Colors.deepPurple[700],
        primarySwatch: Colors.deepPurple,
        primaryColorDark: Colors.deepPurple[800],
      ),
      home: AccountScreen(),
    );
  }
}

String getBaseUrlAPI() {
  if (environment != null) {
    return "http://" + environment["baseUrl"];
  }

  if (!kIsWeb && Platform.isAndroid) {
    return "http://10.0.2.2:8080";
  }

  return "http://localhost:8080";
}

String getBaseUrlWS() {
  if (environment != null) {
    return "ws://" + environment["baseUrl"];
  }

  if (!kIsWeb && Platform.isAndroid) {
    return "ws://10.0.2.2:8080";
  }

  return "ws://localhost:8080";
}
