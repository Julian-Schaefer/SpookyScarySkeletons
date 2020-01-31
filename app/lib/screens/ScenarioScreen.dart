import 'dart:convert';

import 'package:app/Util.dart';
import 'package:app/model/ScenarioEndpoint.dart';
import 'package:app/screens/LongJourneyScreen.dart';
import 'package:app/screens/WrongNumberScreen.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../App.dart';
import 'package:app/model/WebSocket.dart';

class ScenarioScreen extends StatefulWidget {
  @override
  _ScenarioScreenState createState() => _ScenarioScreenState();
}

class _ScenarioScreenState extends State<ScenarioScreen> {
  Future<List<ScenarioEndpoint>> _scenarioEndpoints;

  Future<List<ScenarioEndpoint>> getAvailableScenarios() async {
    String username = await Util.loadUsername();
    http.Response response = await http.get(getBaseUrlAPI() + '/api/scenarios');

    var scenarioEndpoints = new List<ScenarioEndpoint>();
    if (response.statusCode == 200) {
      for (var jsonObject in jsonDecode(response.body)) {
        var scenarioEndpoint = ScenarioEndpoint.fromJSON(jsonObject);
        scenarioEndpoint.websocketEndpoint =
            scenarioEndpoint.websocketEndpoint + "/" + username;
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
              WebSocket webSocket = WebSocket();
              return Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  for (var scenarioEndpoint in snapshot.data)
                    Expanded(
                      child: Container(
                        decoration: BoxDecoration(
                          image: DecorationImage(
                            image: NetworkImage(getBaseUrlAPI() +
                                scenarioEndpoint.backgroundImageUrl),
                            fit: BoxFit.fill,
                          ),
                        ),
                        child: FlatButton(
                          child: Text(
                            scenarioEndpoint.name,
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 32,
                              fontStyle: FontStyle.italic,
                            ),
                          ),
                          onPressed: () {
                            switch (scenarioEndpoint.name) {
                              case 'Sorry, wrong number.':
                                Navigator.pushReplacement(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => WrongNumberScreen(
                                            scenarioEndpoint: scenarioEndpoint,
                                            webSocket: webSocket,
                                          )),
                                );
                                break;
                              case 'A long journey.':
                                Navigator.pushReplacement(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => LongJourneyScreen(
                                            scenarioEndpoint: scenarioEndpoint,
                                            webSocket: webSocket,
                                          )),
                                );
                                break;
                              default:
                                this._showDialog('Scenario was not found: ' +
                                    scenarioEndpoint.name);
                            }
                          },
                        ),
                      ),
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
