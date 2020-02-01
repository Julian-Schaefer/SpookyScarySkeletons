import 'package:app/model/Scenario.dart';
import 'package:app/model/WebSocket.dart';
import 'package:app/screens/ChatScreenWidget.dart';
import 'package:flutter/material.dart';

class WrongNumberScreen extends StatelessWidget {
  final Scenario scenario;
  final WebSocket webSocket;

  const WrongNumberScreen({this.scenario, this.webSocket});

  @override
  Widget build(BuildContext context) {
    return Theme(
        data: ThemeData(
          primaryColor: Colors.deepPurpleAccent,
          primaryColorDark: Colors.deepPurple,
          cardColor: Colors.white,
        ),
        child: ChatScreenWidget(
          scenario: scenario,
          webSocket: webSocket,
          valueText: "Vertrauen",
        ));
  }
}
