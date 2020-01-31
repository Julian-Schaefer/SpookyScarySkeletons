import 'package:app/model/ScenarioEndpoint.dart';
import 'package:app/model/WebSocket.dart';
import 'package:app/screens/ChatScreenWidget.dart';
import 'package:flutter/material.dart';

class LongJourneyScreen extends StatelessWidget {
  final ScenarioEndpoint scenarioEndpoint;
  final WebSocket webSocket;

  const LongJourneyScreen({this.scenarioEndpoint, this.webSocket});

  @override
  Widget build(BuildContext context) {
    return Theme(
        data: ThemeData(
          primaryColor: Colors.orange,
          primaryColorDark: Colors.deepOrangeAccent,
          cardColor: Colors.white,
        ),
        child: ChatScreenWidget(
          scenarioEndpoint: scenarioEndpoint,
          webSocket: webSocket,
          valueText: "Verrücktheit",
        ));
  }
}
