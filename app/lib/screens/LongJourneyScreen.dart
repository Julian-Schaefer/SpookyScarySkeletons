import 'package:app/model/Scenario.dart';
import 'package:app/model/WebSocket.dart';
import 'package:app/screens/ChatScreenWidget.dart';
import 'package:flutter/material.dart';

class LongJourneyScreen extends StatelessWidget {
  final Scenario scenario;
  final WebSocket webSocket;

  const LongJourneyScreen({this.scenario, this.webSocket});

  @override
  Widget build(BuildContext context) {
    return Theme(
        data: ThemeData(
          primaryColor: Colors.orange,
          primaryColorDark: Colors.deepOrangeAccent,
          cardColor: Colors.white,
        ),
        child: ChatScreenWidget(
          title: "A long journey...",
          scenario: scenario,
          webSocket: webSocket,
          valueText: "Verrücktheit",
        ));
  }
}
