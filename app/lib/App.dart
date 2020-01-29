import 'package:flutter/material.dart';
import 'dart:io' show Platform;

import 'screens/AccountScreen.dart';

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

String getBaseUrlAPI() {
  if (Platform.isAndroid) {
    return "http://10.0.2.2:8080";
  }
  return "http://localhost:8080";
}

String getBaseUrlWS() {
  if (Platform.isAndroid) {
    return "ws://10.0.2.2:8080";
  }
  return "ws://localhost:8080";
}
