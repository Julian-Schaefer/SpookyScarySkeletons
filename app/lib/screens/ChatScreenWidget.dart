import 'dart:convert';

import 'package:app/model/ChatMessage.dart';
import 'package:app/model/Choice.dart';
import 'package:app/model/Response.dart';
import 'package:app/model/ScenarioEndpoint.dart';
import 'package:app/widgets/AnswerSlider.dart';
import 'package:app/widgets/GameOverWidget.dart';
import 'package:app/widgets/ScaleWidget.dart';
import 'package:flutter/material.dart';

import 'package:app/model/WebSocket.dart';

import '../App.dart';

class ChatScreenWidget extends StatefulWidget {
  final ScenarioEndpoint scenarioEndpoint;
  final WebSocket webSocket;
  final String valueText;

  const ChatScreenWidget(
      {this.scenarioEndpoint, this.webSocket, this.valueText});

  @override
  _ChatScreenWidgetState createState() => _ChatScreenWidgetState();
}

class _ChatScreenWidgetState extends State<ChatScreenWidget> {
  List<ChatMessage> _messages = new List();
  bool _gameOver = false;
  Choice _firstChoice;
  Choice _secondChoice;
  int _value = 0;

  final _scaffoldKey = GlobalKey<ScaffoldState>();
  final TextEditingController textEditingController =
      new TextEditingController();
  final ScrollController listScrollController = new ScrollController();

  @override
  void initState() {
    super.initState();

    widget.webSocket.connect(widget.scenarioEndpoint.websocketEndpoint);
    widget.webSocket.getStream().listen((responseString) {
      var response = Response.fromJSON(jsonDecode(responseString));
      if (response.type == ResponseType.MESSAGE) {
        var message = response.getMessage();

        setState(() {
          _firstChoice = message.firstChoice;
          _secondChoice = message.secondChoice;

          _messages.insert(0, ChatMessage(true, message.content, "now"));
          listScrollController.animateTo(0.0,
              duration: Duration(milliseconds: 300), curve: Curves.easeOut);
        });
      } else if (response.type == ResponseType.INFORMATION) {
        if (_scaffoldKey.currentState != null) {
          final snackBar = SnackBar(
            content: Text(response.getInformation()),
          );
          _scaffoldKey.currentState.showSnackBar(snackBar);
        }
      } else if (response.type == ResponseType.VALUE_CHANGE) {
        setState(() {
          _value = response.getValueChange();
        });
      } else if (response.type == ResponseType.GAME_OVER) {
        setState(() {
          _gameOver = true;
        });
      }
    });
  }

  @override
  void dispose() {
    widget.webSocket.close();
    super.dispose();
  }

  void sendToWebSocket(String message) {
    widget.webSocket.send(message);
  }

  void _onChoiceSelected(Choice choice) {
    setState(() {
      _messages.insert(0, ChatMessage(false, choice.content, "now"));
      _firstChoice = null;
      _secondChoice = null;
    });

    sendToWebSocket(jsonEncode(choice.toJSON()));

    textEditingController.clear();
    listScrollController.animateTo(0.0,
        duration: Duration(milliseconds: 300), curve: Curves.easeOut);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: Text(
          "Unknown number",
          style: TextStyle(color: Theme.of(context).cardColor),
        ),
      ),
      body: Stack(
        children: <Widget>[
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: NetworkImage(getBaseUrlAPI() +
                    widget.scenarioEndpoint.backgroundImageUrl),
                fit: BoxFit.fill,
              ),
            ),
            child: SafeArea(
              child: !_gameOver
                  ? Column(
                      children: <Widget>[
                        // List of messages
                        buildListMessage(),
                        AnswerSlider(
                          themeData: Theme.of(context),
                          firstChoice: _firstChoice,
                          secondChoice: _secondChoice,
                          onChoiceSelected: (choice) {
                            _onChoiceSelected(choice);
                          },
                        ),
                        // Input content
                        //buildInput(),
                      ],
                    )
                  : GameOverWidget(),
            ),
          ),
          if (!_gameOver)
            Positioned(
                top: 10,
                right: 10,
                child: ScaleWidget(
                  text: widget.valueText,
                  value: _value,
                ))
        ],
      ),
    );
  }

  Widget buildListMessage() {
    return Flexible(
      child: ListView.builder(
        padding: EdgeInsets.all(10.0),
        itemBuilder: (context, index) => buildItem(index, _messages[index]),
        itemCount: _messages.length,
        reverse: true,
        controller: listScrollController,
      ),
    );
  }

  // Widget buildInput() {
  //   return Container(
  //     child: Row(
  //       children: <Widget>[
  //         // Edit text
  //         Flexible(
  //           child: Container(
  //             child: TextField(
  //               style: TextStyle(fontSize: 15.0),
  //               controller: textEditingController,
  //               decoration: InputDecoration.collapsed(
  //                 hintText: 'Type your message...',
  //                 hintStyle: TextStyle(color: Colors.grey),
  //               ),
  //             ),
  //             padding: EdgeInsets.only(left: 15),
  //           ),
  //         ),

  //         // Button send message
  //         Material(
  //           child: new Container(
  //             margin: new EdgeInsets.symmetric(horizontal: 8.0),
  //             child: new IconButton(
  //               icon: new Icon(Icons.send),
  //               onPressed: () => print("Hallo"),
  //               //onPressed: () => onSendMessage(textEditingController.text),
  //               color: _themeData.primaryColor,
  //             ),
  //           ),
  //           color: Colors.white,
  //         ),
  //       ],
  //     ),
  //     width: double.infinity,
  //     height: 50.0,
  //     decoration: new BoxDecoration(
  //         border:
  //             new Border(top: new BorderSide(color: Colors.grey, width: 0.5)),
  //         color: Colors.white),
  //   );
  // }

  Widget buildItem(int index, ChatMessage message) {
    if (!message.isIncoming) {
      // Right (my message)
      return Row(
        children: <Widget>[
          Container(
            child: Column(
              children: <Widget>[
                Container(
                  child: Text(
                    message.content,
                    style: TextStyle(color: Theme.of(context).primaryColor),
                  ),
                  margin: EdgeInsets.only(left: 10.0),
                  width: 200.0,
                ),
                Container(
                  child: Text(
                    message.time,
                    style: TextStyle(
                        color: Theme.of(context).primaryColor,
                        fontSize: 12.0,
                        fontStyle: FontStyle.italic),
                    textAlign: TextAlign.end,
                  ),
                  width: 200.0,
                ),
              ],
            ),
            padding: EdgeInsets.fromLTRB(15.0, 10.0, 15.0, 10.0),
            width: 200.0,
            decoration: BoxDecoration(
                color: Color.fromRGBO(222, 222, 222, 1),
                borderRadius: BorderRadius.circular(8.0)),
            margin: EdgeInsets.only(
                bottom: isLastMessageRight(index) ? 20.0 : 10.0, right: 10.0),
          )
        ],
        mainAxisAlignment: MainAxisAlignment.end,
      );
    } else {
      // Left (peer message)
      return Container(
        child: Column(
          children: <Widget>[
            Container(
              child: Column(
                children: <Widget>[
                  Container(
                    child: Text(
                      message.content,
                      style: TextStyle(color: Colors.white),
                    ),
                    margin: EdgeInsets.only(left: 10.0),
                    width: 200.0,
                  ),
                  Container(
                    child: Text(
                      message.time,
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: 12.0,
                          fontStyle: FontStyle.italic),
                      textAlign: TextAlign.end,
                    ),
                    width: 200.0,
                  ),
                ],
              ),
              padding: EdgeInsets.fromLTRB(15.0, 10.0, 15.0, 10.0),
              width: 200.0,
              decoration: BoxDecoration(
                  color: Theme.of(context).primaryColor,
                  borderRadius: BorderRadius.circular(8.0)),
            )
          ],
          crossAxisAlignment: CrossAxisAlignment.start,
        ),
        margin: EdgeInsets.only(bottom: 10.0),
      );
    }
  }

  bool isLastMessageLeft(int index) {
    if ((index > 0 && _messages != null && _messages[index - 1].isIncoming) ||
        index == 0) {
      return true;
    } else {
      return false;
    }
  }

  bool isLastMessageRight(int index) {
    if ((index > 0 && _messages != null && !_messages[index - 1].isIncoming) ||
        index == 0) {
      return true;
    } else {
      return false;
    }
  }
}
