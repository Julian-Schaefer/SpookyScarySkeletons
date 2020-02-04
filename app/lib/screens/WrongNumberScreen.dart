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
          primaryColor: Color(0xffF10935),
          primaryColorDark: Color(0xffF10935),
          cardColor: Colors.white,
          brightness: Brightness.dark,
        canvasColor: Color(0xff121212),
        ),
        child: ChatScreenWidget(
          title: "Unknown number",
          scenario: scenario,
          webSocket: webSocket,
          valueText: "Vertrauen",
        ));
  }
}
