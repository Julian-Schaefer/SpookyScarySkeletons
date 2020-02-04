import 'package:app/model/GameOver.dart';
import 'package:app/screens/ScenarioScreen.dart';
import 'package:flutter/material.dart';

class GameOverWidget extends StatelessWidget {
  final GameOver gameOver;

  GameOverWidget({this.gameOver});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        padding: EdgeInsets.all(20),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.all(Radius.circular(12)),
          border: Border.all(color: Theme.of(context).primaryColor, width: 5),
          boxShadow: [
            BoxShadow(
              color: Colors.grey.withOpacity(0.7),
              spreadRadius: 4,
              blurRadius: 4,
              offset: Offset(0, 0), // changes position of shadow
            ),
          ],
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Text(
              gameOver.won ? "You won!" : "You lost!",
              style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.black),
            ),
            Padding(
              padding: EdgeInsets.only(top: 10),
            ),
            Text(
              gameOver.message,
              style: TextStyle(color: Colors.black),
            ),
            Padding(
              padding: EdgeInsets.only(top: 10),
            ),
            MaterialButton(
              child: Text(
                'Back to the Scenarios',
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () {
                Navigator.pushReplacement(context,
                    MaterialPageRoute(builder: (context) => ScenarioScreen()));
              },
              color: Theme.of(context).primaryColorDark,
            ),
          ],
        ),
      ),
    );
  }
}
