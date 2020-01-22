import 'dart:convert';

import 'package:app/model/ChatMessage.dart';
import 'package:app/model/Choice.dart';
import 'package:app/model/Message.dart';
import 'package:app/model/ScenarioEndpoint.dart';
import 'package:flutter/material.dart';

import 'package:app/model/WebSocket.dart';

import '../App.dart';

class ChatScreen extends StatefulWidget {
  final ScenarioEndpoint scenarioEndpoint;
  final WebSocket webSocket;

  const ChatScreen({this.scenarioEndpoint, this.webSocket});

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen>
    with SingleTickerProviderStateMixin {
  List<ChatMessage> _messages = createChatMessages();
  Choice _firstChoice;
  Choice _secondChoice;

  AnimationController _animationController;
  Animation<Offset> _outAnimation;
  Animation<Offset> _inAnimation;

  final TextEditingController textEditingController =
      new TextEditingController();
  final ScrollController listScrollController = new ScrollController();

  final ThemeData _themeData = ThemeData(
    primaryColor: Colors.deepPurpleAccent,
    primaryColorDark: Colors.deepPurpleAccent[800],
  );

  @override
  void initState() {
    super.initState();

    _animationController = AnimationController(
      duration: const Duration(milliseconds: 800),
      vsync: this,
    );

    _outAnimation = Tween<Offset>(
      begin: Offset.zero,
      end: const Offset(0.0, 1.0),
    ).animate(CurvedAnimation(
        parent: _animationController, curve: new Interval(0, 0.4)));

    _inAnimation = Tween<Offset>(
      begin: const Offset(0.0, 1.0),
      end: Offset.zero,
    ).animate(CurvedAnimation(
        parent: _animationController, curve: new Interval(0.6, 1)));

    widget.webSocket.connect(widget.scenarioEndpoint.websocketEndpoint);
    widget.webSocket.getStream().listen((response) {
      var message = Message.fromJSON(jsonDecode(response));

      setState(() {
        _firstChoice = message.firstChoice;
        _secondChoice = message.secondChoice;

        _messages.insert(0, ChatMessage(true, message.content, "now"));
        listScrollController.animateTo(0.0,
            duration: Duration(milliseconds: 300), curve: Curves.easeOut);
      });
    });
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  void sendToWebSocket(String message) {
    widget.webSocket.send(message);
  }

  void _onChoiceSelected(Choice choice) {
    setState(() {
      _messages.insert(0, ChatMessage(false, choice.content, "now"));
    });

    sendToWebSocket(jsonEncode(choice.toJson()));

    textEditingController.clear();
    listScrollController.animateTo(0.0,
        duration: Duration(milliseconds: 300), curve: Curves.easeOut);
  }

  @override
  Widget build(BuildContext context) {
    return Theme(
      data: _themeData,
      child: Scaffold(
        appBar: AppBar(
          title: Text("Unknown number"),
        ),
        body: Container(
          decoration: BoxDecoration(
            image: DecorationImage(
              image: NetworkImage(
                  baseUrl + widget.scenarioEndpoint.backgroundImageUrl),
              fit: BoxFit.fill,
            ),
          ),
          child: Column(
            children: <Widget>[
              // List of messages
              buildListMessage(),
              Stack(
                alignment: Alignment.bottomCenter,
                children: <Widget>[
                  SlideTransition(
                    position: _outAnimation,
                    child: Container(
                      width: double.infinity,
                      height: 100,
                      child: FlatButton(
                        child: Text(
                          'Antworten',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 20,
                          ),
                        ),
                        color: _themeData.primaryColorDark,
                        onPressed: () {
                          _animationController.forward();
                        },
                      ),
                    ),
                  ),
                  SlideTransition(
                    position: _inAnimation,
                    child: Container(
                      width: double.infinity,
                      padding: EdgeInsets.fromLTRB(10, 0, 10, 10),
                      child: Row(
                        children: <Widget>[
                          Expanded(
                            child: FlatButton(
                              child: Text(
                                _firstChoice?.content ?? "",
                                style: TextStyle(
                                    color: Colors.white, fontSize: 15),
                              ),
                              color: _themeData.primaryColorDark,
                              onPressed: () {
                                _onChoiceSelected(_firstChoice);
                                _animationController.reverse();
                              },
                            ),
                          ),
                          Padding(
                            padding: EdgeInsets.all(5),
                          ),
                          Expanded(
                            child: FlatButton(
                              child: Text(
                                _secondChoice?.content ?? "",
                                style: TextStyle(
                                    color: Colors.white, fontSize: 15),
                              ),
                              color: _themeData.primaryColorDark,
                              onPressed: () {
                                _onChoiceSelected(_secondChoice);
                                _animationController.reverse();
                              },
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              // Input content
              //buildInput(),
            ],
          ),
        ),
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

  Widget buildInput() {
    return Container(
      child: Row(
        children: <Widget>[
          // Edit text
          Flexible(
            child: Container(
              child: TextField(
                style: TextStyle(fontSize: 15.0),
                controller: textEditingController,
                decoration: InputDecoration.collapsed(
                  hintText: 'Type your message...',
                  hintStyle: TextStyle(color: Colors.grey),
                ),
              ),
              padding: EdgeInsets.only(left: 15),
            ),
          ),

          // Button send message
          Material(
            child: new Container(
              margin: new EdgeInsets.symmetric(horizontal: 8.0),
              child: new IconButton(
                icon: new Icon(Icons.send),
                onPressed: () => print("Hallo"),
                //onPressed: () => onSendMessage(textEditingController.text),
                color: _themeData.primaryColor,
              ),
            ),
            color: Colors.white,
          ),
        ],
      ),
      width: double.infinity,
      height: 50.0,
      decoration: new BoxDecoration(
          border:
              new Border(top: new BorderSide(color: Colors.grey, width: 0.5)),
          color: Colors.white),
    );
  }

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
                    style: TextStyle(color: _themeData.primaryColor),
                  ),
                  margin: EdgeInsets.only(left: 10.0),
                  width: 200.0,
                ),
                Container(
                  child: Text(
                    message.time,
                    style: TextStyle(
                        color: _themeData.primaryColor,
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
                  color: _themeData.primaryColor,
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
