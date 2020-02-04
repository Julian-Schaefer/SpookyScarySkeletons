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
          primaryColor: Colors.orange[400],
          primaryColorDark: Colors.orange[400],
          cardColor: Colors.white,
          brightness: Brightness.dark,
        canvasColor: Color(0xff121212),
        ),
        child: ChatScreenWidget(
          title: "A Long Journey.",
          scenario: scenario,
          webSocket: webSocket,
          valueText: "Sanity",
        ));
  }
}
