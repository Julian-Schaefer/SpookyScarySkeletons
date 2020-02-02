import 'dart:convert';

import 'package:app/model/Score.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../App.dart';

class HighScoreScreen extends StatefulWidget {
  @override
  _HighScoreScreenState createState() => _HighScoreScreenState();
}

class _HighScoreScreenState extends State<HighScoreScreen> {
  Future<List<Score>> _highScores;

  @protected
  @mustCallSuper
  void initState() {
    super.initState();
    _highScores = _getHighScores();
  }

  Future<List<Score>> _getHighScores() async {
    var scores = new List<Score>();
    var response = await http.get(getBaseUrlAPI() + '/api/scores/highscore');

    if (response.statusCode == 200) {
      for (var jsonObject in jsonDecode(response.body)) {
        var score = Score.fromJSON(jsonObject);
        scores.add(score);
      }

      return scores;
    } else {
      throw Exception('Could not load highscores.');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('High Scores')),
      body: Center(
        child: FutureBuilder<List<Score>>(
            future: _highScores,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return ListView.builder(
                    itemCount: snapshot.data.length,
                    itemBuilder: (BuildContext ctxt, int index) {
                      return new Container(
                        height: 80,
                        padding: EdgeInsets.fromLTRB(50, 0, 50, 0),
                        decoration: BoxDecoration(
                            border: Border(
                                bottom: BorderSide(
                                    color: Theme.of(context).primaryColorDark,
                                    width: 3))),
                        child: Center(
                            child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: <Widget>[
                            Text((index + 1).toString() + "."),
                            Text(snapshot.data[index].username),
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
