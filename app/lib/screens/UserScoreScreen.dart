import 'dart:convert';

import 'package:app/model/Score.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:gradient_app_bar/gradient_app_bar.dart';

import '../App.dart';
import '../Util.dart';

class UserScoreScreen extends StatefulWidget {
  @override
  _UserScoreScreenState createState() => _UserScoreScreenState();
}

class _UserScoreScreenState extends State<UserScoreScreen> {
  Future<List<Score>> _userScores;
  String _username;

  @protected
  @mustCallSuper
  void initState() {
    super.initState();
    _userScores = _getUserScores();
  }

  Future<List<Score>> _getUserScores() async {
    _username = await Util.loadUsername();

    setState(() {
      _username = _username;
    });

    var scores = new List<Score>();
    var response = await http.get(getBaseUrlAPI() + '/api/scores/' + _username);

    if (response.statusCode == 200) {
      for (var jsonObject in jsonDecode(response.body)) {
        var score = Score.fromJSON(jsonObject);
        scores.add(score);
      }

      return scores;
    } else {
      throw Exception('Could not load scores for username: ' + _username);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: GradientAppBar(
          title: Text('Meine Scores: ' + (_username != null ? _username : "")),
          centerTitle: true,
          gradient: LinearGradient(colors: [Color(0xff523755), Color(0xff00213E)])
          ),
      body: Center(
        child: FutureBuilder<List<Score>>(
            future: _userScores,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return ListView.builder(
                    itemCount: snapshot.data.length,
                    itemBuilder: (BuildContext ctxt, int index) {
                      return new Container(
                        height: 80,
                        padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                        decoration: BoxDecoration(
                            border: Border(
                                bottom: BorderSide(
                                    color: Theme.of(context).primaryColorDark,
                                    width: 3))),
                        child: Center(
                            child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: <Widget>[
                            Text(snapshot.data[index].dateTime),
                            Text(snapshot.data[index].scenario),
                            Text(snapshot.data[index].duration),
                          ],
                        )),
                      );
                    });
              } else if (snapshot.hasError) {
                return Text("${snapshot.error}");
              }

              // By default, show a loading spinner.
              return CircularProgressIndicator();
            }),
      ),
    );
  }
}
