import 'dart:convert';

import 'package:app/model/ScenarioEndpoint.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/foundation.dart';

import 'ChatScreen.dart';
import 'package:app/model/WebSocket.dart';

class StartScreen extends StatefulWidget {
  @override
  _StartScreenState createState() => _StartScreenState();
}

class _StartScreenState extends State<StartScreen> {
  Future<List<ScenarioEndpoint>> _scenarioEndpoints;

  Future<List<ScenarioEndpoint>> getAvailableScenarios() async {
    http.Response response;

    if (kIsWeb) {
      response = await http.get('http://localhost:8080/api/scenarios');
    } else {
      response = await http.get('http://10.0.2.2:8080/api/scenarios');
    }
    var scenarioEndpoints = new List<ScenarioEndpoint>();
    if (response.statusCode == 200) {
      for (var jsonObject in jsonDecode(response.body)) {
        var scenarioEndpoint = ScenarioEndpoint.fromJSON(jsonObject);
        scenarioEndpoints.add(scenarioEndpoint);
      }

      return scenarioEndpoints;
    } else {
      // If that response was not OK, throw an error.
      throw Exception('Failed to load post');
    }
  }

  @override
  void initState() {
    super.initState();
    _scenarioEndpoints = getAvailableScenarios();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Spooky Scary Skeletons'),
      ),
      body: Center(
        child: FutureBuilder<List<ScenarioEndpoint>>(
          future: _scenarioEndpoints,
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              WebSocket myWebSocket = WebSocket();
              return Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  for (var scenarioEndpoint in snapshot.data)
                    RaisedButton(
                      child: Text(scenarioEndpoint.name),
                      onPressed: () {
                        switch (scenarioEndpoint.name) {
                          case 'Sorry, wrong number.':
                            Navigator.pushReplacement(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => ChatScreen(
                                        scenarioEndpoint: scenarioEndpoint,
                                        webSocket: myWebSocket,
                                      )),
                            );
                            break;
                          default:
                            this._showDialog('Scenario was not found: ' +
                                scenarioEndpoint.name);
                        }
                      },
                    ),
                ],
              );
            } else if (snapshot.hasError) {
              return Text("${snapshot.error}");
            }

            // By default, show a loading spinner.
            return CircularProgressIndicator();
          },
        ),
      ),
    );
  }

  void _showDialog(String message) {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Something went wrong...'),
            content: Text(message),
            actions: <Widget>[
              FlatButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }
}
